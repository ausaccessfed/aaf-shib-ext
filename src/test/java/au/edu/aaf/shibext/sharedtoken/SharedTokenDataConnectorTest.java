package au.edu.aaf.shibext.sharedtoken;

import net.shibboleth.idp.attribute.resolver.ResolutionException;
import net.shibboleth.idp.attribute.resolver.context.AttributeResolutionContext;
import net.shibboleth.idp.attribute.resolver.context.AttributeResolverWorkContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class SharedTokenDataConnectorTest {

    @InjectMocks
    private SharedTokenDataConnector sharedTokenDataConnector;

    @Test
    public void testDoDataConnectorResolve() throws ResolutionException {
        AttributeResolutionContext resolutionContext = mock(AttributeResolutionContext.class);

        sharedTokenDataConnector.doDataConnectorResolve(resolutionContext, new AttributeResolverWorkContext());

        assertThat(sharedTokenDataConnector, is(notNullValue()));
    }

    @Test
    public void testGetGeneratedAttributeId() {
        assertThat(sharedTokenDataConnector.getGeneratedAttributeId(), is(nullValue()));
    }

    @Test
    public void testSetGeneratedAttributeId() {
        sharedTokenDataConnector.setGeneratedAttributeId("test");
        assertThat(sharedTokenDataConnector.getGeneratedAttributeId(), is("test"));
    }

    public SharedTokenDataConnector getSharedTokenDataConnector() {
        return sharedTokenDataConnector;
    }

    public void setSharedTokenDataConnector(SharedTokenDataConnector sharedTokenDataConnector) {
        this.sharedTokenDataConnector = sharedTokenDataConnector;
    }
}

