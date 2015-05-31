package au.edu.aaf.shibext.sharedtoken;

import net.shibboleth.idp.attribute.resolver.context.AttributeResolutionContext;
import net.shibboleth.idp.attribute.resolver.context.AttributeResolverWorkContext;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;

/**
 *
 */
public class SharedTokenDataConnectorTest {

    @Test
    public void testDoDataConnectorResolve() throws Exception {
        SharedTokenDataConnector sharedTokenDataConnector = new SharedTokenDataConnector();
        AttributeResolutionContext resolutionContext = mock(AttributeResolutionContext.class);

        sharedTokenDataConnector.doDataConnectorResolve(resolutionContext, new AttributeResolverWorkContext());

        assertThat(sharedTokenDataConnector,
                is(notNullValue()));
    }
}

