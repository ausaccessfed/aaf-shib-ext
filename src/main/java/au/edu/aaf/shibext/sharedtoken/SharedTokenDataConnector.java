package au.edu.aaf.shibext.sharedtoken;

import net.shibboleth.idp.attribute.IdPAttribute;
import net.shibboleth.idp.attribute.IdPAttributeValue;
import net.shibboleth.idp.attribute.StringAttributeValue;
import net.shibboleth.idp.attribute.resolver.AbstractDataConnector;
import net.shibboleth.idp.attribute.resolver.ResolutionException;
import net.shibboleth.idp.attribute.resolver.ResolvedAttributeDefinition;
import net.shibboleth.idp.attribute.resolver.context.AttributeResolutionContext;
import net.shibboleth.idp.attribute.resolver.context.AttributeResolverWorkContext;
import net.shibboleth.utilities.java.support.collection.LazyMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Retrieves an auEduPersonSharedToken. This value may exist in the database, otherwise a new value is generated.
 *
 * @author rianniello
 * @see AuEduPersonSharedTokenGenerator
 */
public class SharedTokenDataConnector extends AbstractDataConnector {

    private static final Logger LOG = LoggerFactory.getLogger(SharedTokenDataConnector.class);

    /**
     * Name of the source attribute used for the auEduPersonSharedToken generation. Ideally a unique identifier
     * that never changes.
     */
    private String sourceAttributeId;

    /**
     * The name generated by the connector.
     */
    private String generatedAttributeId;

    /**
     * The salt used in auEduPersonSharedToken generation algorithm.
     */
    private String salt;

    /**
     * Used for auEduPersonSharedToken generation.
     */
    private AuEduPersonSharedTokenGenerator auEduPersonSharedTokenGenerator = new AuEduPersonSharedTokenGenerator();

    /**
     * Resolves the generatedAttributeId (auEduPersonSharedToken) value.
     *
     * @throws net.shibboleth.idp.attribute.resolver.ResolutionException if sourceAttributeId cannot be resolved
     */
    @Override
    protected Map<String, IdPAttribute> doDataConnectorResolve(AttributeResolutionContext resolutionContext,
                                                               AttributeResolverWorkContext workContext)
            throws ResolutionException {

        LOG.debug("Resolving SharedToken");
        LOG.debug("generatedAttributeId is " + generatedAttributeId);
        LOG.debug("sourceAttributeId is " + sourceAttributeId);

        String resolvedSourceIdAttribute = getSourceAttributeValue(workContext);
        String idpIdentifier = resolutionContext.getAttributeIssuerID();

        LOG.debug("Generating auEduPersonSharedToken. Resolved sourceAttributeId as {}"
                 + ", idpIdentifier is {}, salt is {}", resolvedSourceIdAttribute, idpIdentifier, salt);

        // TODO generation if not in database, otherwise use db value
        String auEduPersonSharedToken = auEduPersonSharedTokenGenerator.generate(resolvedSourceIdAttribute,
                idpIdentifier, salt);

        IdPAttribute auEduPersonSharedTokenAttribute = buildAuEduPersonSharedTokenAttribute(auEduPersonSharedToken);
        Map<String, IdPAttribute> attributes = new LazyMap<>();
        attributes.put(auEduPersonSharedTokenAttribute.getId(), auEduPersonSharedTokenAttribute);

        return attributes;
    }

    private IdPAttribute buildAuEduPersonSharedTokenAttribute(String resolvedSourceAttribute) {
        IdPAttribute attribute = new IdPAttribute(this.generatedAttributeId);
        Collection<IdPAttributeValue<String>> attributeValues = new ArrayList<>();
        attributeValues.add(new StringAttributeValue(resolvedSourceAttribute));
        attribute.setValues(attributeValues);
        return attribute;
    }

    private String getSourceAttributeValue(AttributeResolverWorkContext workContext) throws
            ResolutionException {
        LOG.debug("Getting sourceAttributeId '" + sourceAttributeId + "' from resolvedAttributes");
        Map<String, ResolvedAttributeDefinition> resolvedAttributes = workContext.getResolvedIdPAttributeDefinitions();
        if (resolvedAttributes.get(sourceAttributeId) == null) {
            throwResolutionException("Value '" + sourceAttributeId + "' could not be resolved");
        }

        IdPAttribute resolvedAttribute = resolvedAttributes.get(sourceAttributeId).getResolvedAttribute();
        List<IdPAttributeValue<?>> values = resolvedAttribute.getValues();

        if (values == null || values.size() != 1) {
            throwResolutionException("Value '" + sourceAttributeId + "' could not be resolved");
        }

        Object resolvedSourceAttributeObject = values.get(0);
        if (!StringAttributeValue.class.equals(resolvedSourceAttributeObject.getClass())) {
            throwResolutionException("Value '" + sourceAttributeId + "' must resolve to a String");
        }

        String resolvedSourceAttribute = ((StringAttributeValue) resolvedSourceAttributeObject).getValue();
        LOG.debug("Resolved as " + resolvedSourceAttribute);
        return resolvedSourceAttribute;

    }

    private void throwResolutionException(String message) throws ResolutionException {
        LOG.error(message);
        throw new ResolutionException(message);
    }

    /**
     * {@link SharedTokenDataConnector#generatedAttributeId}.
     * @param generatedAttributeId {@link SharedTokenDataConnector#generatedAttributeId}.
     */
    public void setGeneratedAttributeId(String generatedAttributeId) {
        this.generatedAttributeId = generatedAttributeId;
    }

    /**
     * {@link SharedTokenDataConnector#sourceAttributeId}.
     * @param sourceAttributeId {@link SharedTokenDataConnector#sourceAttributeId}.
     */
    public void setSourceAttributeId(String sourceAttributeId) {
        this.sourceAttributeId = sourceAttributeId;
    }

    /**
     * {@link SharedTokenDataConnector#salt}.
     * @param salt {@link SharedTokenDataConnector#salt}.
     */
    public void setSalt(String salt) {
        this.salt = salt;
    }
}
