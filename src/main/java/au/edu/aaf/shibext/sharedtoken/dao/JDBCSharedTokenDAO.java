package au.edu.aaf.shibext.sharedtoken.dao;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

/**
 * JdbcTemplate driven SharedTokenDAO implementation.
 *
 * @author rianniello
 * @see org.springframework.jdbc.core.JdbcTemplate
 */
public class JDBCSharedTokenDAO implements SharedTokenDAO {

    private static final Logger LOG = LoggerFactory.getLogger(JDBCSharedTokenDAO.class);

    private final JdbcTemplate jdbcTemplate;

    /**
     * Instantiate a JdbcTemplate using the input dataSource.
     *
     * @param dataSource A dataSource instance configured for SharedToken data access.
     */
    public JDBCSharedTokenDAO(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * Retrieves the SharedToken corresponding to the uid.
     *
     * @param uid The user identifier (primary key)
     * @param primaryKeyName The name of the primary key
     * @return The SharedToken value or null if not found
     */
    @Override
    public String getSharedToken(String uid, String primaryKeyName) {
        String sqlSelect = (primaryKeyName == null) ? 
                   "select sharedtoken from tb_st where uid = ?" :
                   "select sharedtoken from tb_st where " + primaryKeyName + " = ?";
        LOG.debug("getSharedToken with '{}' '{}'", primaryKeyName, uid);

        verifyArgumentIsNotBlankOrNull(uid, "uid");


        List<String> uids = jdbcTemplate.query(sqlSelect,
                new Object[]{uid}, (rs, rowNum) -> {
                    return rs.getString("sharedtoken");
                });

        if (uids.size() == 1) {
            String sharedToken = uids.get(0);
            LOG.debug("sharedToken: {}", sharedToken);
            return sharedToken;
        }

        LOG.debug("sharedToken not found");
        return null;
    }

    /**
     * Persists SharedToken for an associated uid.
     *
     * @param uid         The user identifier (primary key) to save
     * @param sharedToken the SharedToken value to save
     * @param primaryKeyName The name of the primary key
     */
    @Override
    public void persistSharedToken(String uid, String sharedToken, String primaryKeyName) {
        String sqlInsert = (primaryKeyName == null) ?
                  "insert into tb_st(uid, sharedtoken) values (?, ?)" :
                  "insert into tb_st(" + primaryKeyName + ", sharedtoken) values (?, ?)";
        LOG.debug("Persisting shared token with '{}' '{}' and sharedToken '{}' ", primaryKeyName, uid, sharedToken);

        verifyArgumentIsNotBlankOrNull(uid, "uid");
        verifyArgumentIsNotBlankOrNull(sharedToken, "sharedToken");

        int affectedRows = jdbcTemplate.update(sqlInsert, uid, sharedToken);
        LOG.debug("{} affected rows when persisting shared token", affectedRows);
    }

    private void verifyArgumentIsNotBlankOrNull(String arg, String argumentName) {
        if (StringUtils.isBlank(arg)) {
            throw new IllegalArgumentException(argumentName + " cannot be blank or null");
        }
    }
}
