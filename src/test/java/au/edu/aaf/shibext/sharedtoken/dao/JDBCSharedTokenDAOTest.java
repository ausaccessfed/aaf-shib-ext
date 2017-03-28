package au.edu.aaf.shibext.sharedtoken.dao;

import au.edu.aaf.shibext.config.EmbeddedDataSourceConfig;
import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.naming.NamingException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = EmbeddedDataSourceConfig.class, loader = AnnotationConfigContextLoader.class)
public class JDBCSharedTokenDAOTest {

    private static final String EXISTING_VALID_SHARED_TOKEN = "IpdpeFs-WlMqjaC8l-lO1_tJme8";
    private static final String EXSITING_VALID_UID = "rianniello";
    private static final String NEW_VALID_SHARED_TOKEN = "WlMqjaC8l-pdlMqjs-lO1peF_e8ItJm";
    private static final String NEW_VALID_UID = "smangelsdorf";
    private static final String INVALID_UID = "uid cannot be blank or null";
    private static final String INVALID_SHARED_TOKEN = "sharedToken cannot be blank or null";
    private static final String UID = "uid";

    private JDBCSharedTokenDAO jdbcSharedTokenDAO;

    @Autowired
    private EmbeddedDatabase dataSource;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setup() throws NamingException {
        jdbcSharedTokenDAO = new JDBCSharedTokenDAO(dataSource);
    }

    @Test
    public void ensureThrowsIllegalArgumentExceptionIfDataSourceIsNull() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Property 'dataSource' is required");
        new JDBCSharedTokenDAO(null);
    }

    @Test
    public void checkRetrievesSharedTokenWhenUserExists() {
        assertThat(jdbcSharedTokenDAO.getSharedToken(EXSITING_VALID_UID, UID),
                is(equalTo(EXISTING_VALID_SHARED_TOKEN)));
    }

    @Test
    public void retrievesNullSharedTokenWhenUserDoesNotExist() {
        assertThat(jdbcSharedTokenDAO.getSharedToken("new_user", UID),
                is(nullValue()));
    }

    @Test
    public void getSharedTokenThrowsIllegalArgumentExceptionWhenInputIsNull() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(INVALID_UID);
        jdbcSharedTokenDAO.getSharedToken(null, UID);
    }

    @Test
    public void getSharedTokenThrowsIllegalArgumentExceptionWhenInputIsBlank() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(INVALID_UID);
        jdbcSharedTokenDAO.getSharedToken(StringUtils.EMPTY, UID);
    }

    @Test
    public void persistSharedTokenThrowsIllegalArgumentExceptionWithNullUID() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(INVALID_UID);
        jdbcSharedTokenDAO.persistSharedToken(null, NEW_VALID_SHARED_TOKEN, UID);
    }

    @Test
    public void persistSharedTokenThrowsIllegalArgumentExceptionWithNullSharedToken() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(INVALID_SHARED_TOKEN);
        jdbcSharedTokenDAO.persistSharedToken(NEW_VALID_UID, null, UID);
    }

    @Test
    public void persistSharedTokenThrowsIllegalArgumentExceptionWithBlankUID() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(INVALID_UID);
        jdbcSharedTokenDAO.persistSharedToken(StringUtils.EMPTY, NEW_VALID_SHARED_TOKEN, UID);
    }

    @Test
    public void persistSharedTokenThrowsIllegalArgumentExceptionWithBlankSharedToken() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(INVALID_SHARED_TOKEN);
        jdbcSharedTokenDAO.persistSharedToken(NEW_VALID_UID, StringUtils.EMPTY, UID);
    }

    @Test
    public void persistsSharedTokenAsExpected() {
        jdbcSharedTokenDAO.persistSharedToken(NEW_VALID_UID, NEW_VALID_SHARED_TOKEN, UID);
        assertThat(jdbcSharedTokenDAO.getSharedToken(NEW_VALID_UID, UID),
                is(equalTo(NEW_VALID_SHARED_TOKEN)));
    }

    @Test
    public void persistsSharedTokenWhenAlreadyExistsThrowsException() {
        exception.expect(DuplicateKeyException.class);
        jdbcSharedTokenDAO.persistSharedToken(EXSITING_VALID_UID, NEW_VALID_SHARED_TOKEN, UID);
    }

}
