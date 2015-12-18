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
    private static final String SELECT_SHARED_TOKEN = "select sharedtoken from tb_st where uid = ?";
    private static final String INSERT_SHARED_TOKEN = "insert into tb_st(uid, sharedtoken) values (?, ?)";

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
     * @return The SharedToken value or null if not found
     */
    @Override
    public String getSharedToken(String uid) {
        LOG.debug("getSharedToken with uid '{}'", uid);

        verifyArgumentIsNotBlankOrNull(uid, "uid");

        List<String> uids = jdbcTemplate.query(SELECT_SHARED_TOKEN,
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
     */
    @Override
    public void persistSharedToken(String uid, String sharedToken) {
        LOG.debug("Persisting shared token with uid '{}' and sharedToken '{}' ", uid, sharedToken);

        verifyArgumentIsNotBlankOrNull(uid, "uid");
        verifyArgumentIsNotBlankOrNull(sharedToken, "sharedToken");

        int affectedRows = jdbcTemplate.update(INSERT_SHARED_TOKEN, uid, sharedToken);
        LOG.debug("{} affected rows when persisting shared token", affectedRows);
    }

    private void verifyArgumentIsNotBlankOrNull(String arg, String argumentName) {
        if (StringUtils.isBlank(arg)) {
            throw new IllegalArgumentException(argumentName + " cannot be blank or null");
        }
    }
}
