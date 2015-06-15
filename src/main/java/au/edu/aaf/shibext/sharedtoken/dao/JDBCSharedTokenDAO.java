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
    private static final String QUERY_SELECT_SHARED_TOKEN = "select sharedtoken from tb_st where uid = ?";

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
        if (StringUtils.isBlank(uid)) {
            throw new IllegalArgumentException("uid must not be null or blank");
        }

        LOG.debug("getSharedToken with uid '{}'", uid);
        List<String> uids = jdbcTemplate.query(QUERY_SELECT_SHARED_TOKEN,
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
}
