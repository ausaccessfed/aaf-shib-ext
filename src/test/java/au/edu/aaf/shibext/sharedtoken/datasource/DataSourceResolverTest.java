package au.edu.aaf.shibext.sharedtoken.datasource;

import au.edu.aaf.shibext.config.EmbeddedDataSourceConfig;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.naming.NameNotFoundException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = EmbeddedDataSourceConfig.class, loader = AnnotationConfigContextLoader.class)
public class DataSourceResolverTest {

    private DataSourceResolver dataSourceResolver;

    @Before
    public void setup() {
        dataSourceResolver = new DataSourceResolver();
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void ensureLookupRetrievesDataSourceAsExpected() {
        assertThat(dataSourceResolver.lookup(EmbeddedDataSourceConfig.DATASOURCE_ID),
                is(notNullValue()));
    }

    @Test
    public void ensureLookupThrowsRuntimeExceptionIfNameNotFound() {
        exception.expect(RuntimeException.class);
        exception.expectCause(IsInstanceOf.<Throwable>instanceOf(NameNotFoundException.class));
        dataSourceResolver.lookup("Blah");
    }

}
