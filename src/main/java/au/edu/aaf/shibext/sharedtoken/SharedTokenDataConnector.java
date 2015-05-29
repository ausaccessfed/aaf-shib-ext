package au.edu.aaf.shibext.sharedtoken;

import net.shibboleth.idp.attribute.IdPAttribute;
import net.shibboleth.idp.attribute.resolver.AbstractDataConnector;
import net.shibboleth.idp.attribute.resolver.ResolutionException;
import net.shibboleth.idp.attribute.resolver.context.AttributeResolutionContext;
import net.shibboleth.idp.attribute.resolver.context
        .AttributeResolverWorkContext;
import net.shibboleth.utilities.java.support.collection.LazyMap;

import javax.annotation.Nullable;
import java.util.Map;

/**
 *
 */
public class SharedTokenDataConnector extends AbstractDataConnector {

    private String generatedAttributeId;

    @Nullable
    @Override
    protected Map<String, IdPAttribute> doDataConnectorResolve
            (AttributeResolutionContext resolutionContext,
             AttributeResolverWorkContext workContext) throws
            ResolutionException {
        Map<String, IdPAttribute> attributes = new LazyMap<String,
                IdPAttribute>();
        return attributes;
    }




    /**
     *
     */
    public String getGeneratedAttributeId() {
        return generatedAttributeId;
    }


    /**
     *
     */
    public void setGeneratedAttributeId(String generatedAttributeId) {
        this.generatedAttributeId = generatedAttributeId;
    }
}
