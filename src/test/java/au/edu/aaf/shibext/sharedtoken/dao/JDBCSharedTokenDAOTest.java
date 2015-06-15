package au.edu.aaf.shibext.sharedtoken.dao;

import au.edu.aaf.shibext.config.EmbeddedDataSourceConfig;
import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
        assertThat(jdbcSharedTokenDAO.getSharedToken("rianniello"),
                is(equalTo("IpdpeFs-WlMqjaC8l-lO1_tJme8")));
    }

    @Test
    public void retrievesNullSharedTokenWhenUserDoesNotExist() {
        assertThat(jdbcSharedTokenDAO.getSharedToken("new_user"),
                is(nullValue()));
    }

    @Test
    public void throwIllegalArgumentExceptionWhenInputIsNull() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("uid must not be null or blank");
        jdbcSharedTokenDAO.getSharedToken(null);
    }

    @Test
    public void throwIllegalArgumentExceptionWhenInputIsBlank() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("uid must not be null or blank");
        jdbcSharedTokenDAO.getSharedToken(StringUtils.EMPTY);
    }

}
