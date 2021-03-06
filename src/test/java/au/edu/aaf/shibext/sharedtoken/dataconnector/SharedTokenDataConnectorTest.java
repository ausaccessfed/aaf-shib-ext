package au.edu.aaf.shibext.sharedtoken.dataconnector;

import au.edu.aaf.shibext.config.EmbeddedDataSourceConfig;
import net.shibboleth.idp.attribute.ByteAttributeValue;
import net.shibboleth.idp.attribute.IdPAttribute;
import net.shibboleth.idp.attribute.IdPAttributeValue;
import net.shibboleth.idp.attribute.StringAttributeValue;
import net.shibboleth.idp.attribute.resolver.AttributeDefinition;
import net.shibboleth.idp.attribute.resolver.ResolutionException;
import net.shibboleth.idp.attribute.resolver.ResolvedAttributeDefinition;
import net.shibboleth.idp.attribute.resolver.context.AttributeResolutionContext;
import net.shibboleth.idp.attribute.resolver.context.AttributeResolverWorkContext;
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

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = EmbeddedDataSourceConfig.class, loader = AnnotationConfigContextLoader.class)
public class SharedTokenDataConnectorTest {

    private static final String SOURCE_ATTRIBUTE_ID = "uid";
    private static final String GENERATED_ATTRIBUTE_ID = "auEduPersonSharedToken";
    private static final String IDP_IDENTIFIER = "https://shib3.aaf.dev.edu.au/idp/shibboleth";
    private static final String SALT = "Ez8m1HDSLBxu0JNcPEywmOpy+apq4Niw9kEMmAyWbhJqcfAb";
    private static final String PRIMRARY_KEY_NAME = "uid";
    private static final String UNCHECKED = "unchecked";
    private SharedTokenDataConnector sharedTokenDataConnector;
    private AttributeResolutionContext mockAttributeResolutionContext;
    private AttributeResolverWorkContext mockAttributeResolverWorkContext;

    @Autowired
    private EmbeddedDatabase dataSource;

    @Before
    public void setupSharedTokenDataConnector() {
        sharedTokenDataConnector = new SharedTokenDataConnector();
        sharedTokenDataConnector.setGeneratedAttributeId(GENERATED_ATTRIBUTE_ID);
        sharedTokenDataConnector.setSourceAttributeId(SOURCE_ATTRIBUTE_ID);
        sharedTokenDataConnector.setSalt(SALT);
        sharedTokenDataConnector.setDataSource(dataSource);
        sharedTokenDataConnector.setPrimaryKeyName(PRIMRARY_KEY_NAME);
    }

    @Before
    public void buildMocks() {
        mockAttributeResolutionContext = mock(AttributeResolutionContext.class);
        mockAttributeResolverWorkContext = mock(AttributeResolverWorkContext.class);
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void ensureResolvesAuEduPersonSharedToken() throws ResolutionException {
        final String uniqueUserIdentifier = "12345679";

        @SuppressWarnings(UNCHECKED)
        Map<String, ResolvedAttributeDefinition> mockedAttributeMap = mock(Map.class);

        AttributeDefinition mockedAttributeDefinition = mock(AttributeDefinition.class);
        IdPAttribute mockedIdPAttribute = mock(IdPAttribute.class);
        when(mockedAttributeDefinition.isInitialized()).thenReturn(true);
        when(mockedAttributeDefinition.isDestroyed()).thenReturn(false);
        ResolvedAttributeDefinition resolvedAttributeDefinition = new ResolvedAttributeDefinition(
                mockedAttributeDefinition, mockedIdPAttribute);

        Set<Map.Entry<String, ResolvedAttributeDefinition>> entrySet = new HashSet<>();
        entrySet.add(new AbstractMap.SimpleEntry<>(SOURCE_ATTRIBUTE_ID, resolvedAttributeDefinition));

        List<IdPAttributeValue<?>> idPAttributeValues = new ArrayList<>();
        idPAttributeValues.add(new StringAttributeValue(uniqueUserIdentifier));

        when(mockAttributeResolverWorkContext.getResolvedIdPAttributeDefinitions()).thenReturn(mockedAttributeMap);
        when(mockedAttributeMap.entrySet()).thenReturn(entrySet);
        when(mockedAttributeMap.get(SOURCE_ATTRIBUTE_ID)).thenReturn(resolvedAttributeDefinition);
        when(mockedIdPAttribute.getValues()).thenReturn(idPAttributeValues);
        when(mockAttributeResolutionContext.getAttributeIssuerID()).thenReturn(IDP_IDENTIFIER);
        when(mockAttributeResolutionContext.getPrincipal()).thenReturn(uniqueUserIdentifier);

        Map<String, IdPAttribute> result = sharedTokenDataConnector.doDataConnectorResolve
                (mockAttributeResolutionContext, mockAttributeResolverWorkContext);

        assertThat(result.size(), is(1));
        assertThat(result.containsKey(GENERATED_ATTRIBUTE_ID), is(true));
        assertThat(result.get(GENERATED_ATTRIBUTE_ID).getValues().get(0).getValue(),
                is("rXjBo0Z9gQkCLmz_08IcYCJej6w"));
    }

    @Test
    public void ensureResolvesAuEduPersonSharedTokenForExistingUser() throws ResolutionException {
        final String existingUniqueUserIdentifier = "rianniello";

        @SuppressWarnings(UNCHECKED)
        Map<String, ResolvedAttributeDefinition> mockedAttributeMap = mock(Map.class);

        AttributeDefinition mockedAttributeDefinition = mock(AttributeDefinition.class);
        IdPAttribute mockedIdPAttribute = mock(IdPAttribute.class);
        when(mockedAttributeDefinition.isInitialized()).thenReturn(true);
        when(mockedAttributeDefinition.isDestroyed()).thenReturn(false);
        ResolvedAttributeDefinition resolvedAttributeDefinition = new ResolvedAttributeDefinition(
                mockedAttributeDefinition, mockedIdPAttribute);

        Set<Map.Entry<String, ResolvedAttributeDefinition>> entrySet = new HashSet<>();
        entrySet.add(new AbstractMap.SimpleEntry<>(SOURCE_ATTRIBUTE_ID, resolvedAttributeDefinition));

        List<IdPAttributeValue<?>> idPAttributeValues = new ArrayList<>();
        idPAttributeValues.add(new StringAttributeValue(existingUniqueUserIdentifier));

        when(mockAttributeResolverWorkContext.getResolvedIdPAttributeDefinitions()).thenReturn(mockedAttributeMap);
        when(mockedAttributeMap.entrySet()).thenReturn(entrySet);
        when(mockedAttributeMap.get(SOURCE_ATTRIBUTE_ID)).thenReturn(resolvedAttributeDefinition);
        when(mockedIdPAttribute.getValues()).thenReturn(idPAttributeValues);
        when(mockAttributeResolutionContext.getAttributeIssuerID()).thenReturn(IDP_IDENTIFIER);
        when(mockAttributeResolutionContext.getPrincipal()).thenReturn(existingUniqueUserIdentifier);

        Map<String, IdPAttribute> result = sharedTokenDataConnector.doDataConnectorResolve
                (mockAttributeResolutionContext, mockAttributeResolverWorkContext);

        assertThat(result.size(), is(1));
        assertThat(result.containsKey(GENERATED_ATTRIBUTE_ID), is(true));
        assertThat(result.get(GENERATED_ATTRIBUTE_ID).getValues().get(0).getValue(),
                is("IpdpeFs-WlMqjaC8l-lO1_tJme8"));
    }

    @Test
    public void ensureThatResolutionExceptionIsThrownIfSourceIdentifierCannotBeResolved() throws ResolutionException {
        @SuppressWarnings(UNCHECKED)
        Map<String, ResolvedAttributeDefinition> mockedAttributeMap = mock(Map.class);

        AttributeDefinition mockedAttributeDefinition = mock(AttributeDefinition.class);
        IdPAttribute mockedIdPAttribute = mock(IdPAttribute.class);
        when(mockedAttributeDefinition.isInitialized()).thenReturn(true);
        when(mockedAttributeDefinition.isDestroyed()).thenReturn(false);
        ResolvedAttributeDefinition resolvedAttributeDefinition = new ResolvedAttributeDefinition(
                mockedAttributeDefinition, mockedIdPAttribute);

        List<IdPAttributeValue<?>> idPAttributeValues = new ArrayList<>();

        when(mockAttributeResolverWorkContext.getResolvedIdPAttributeDefinitions()).thenReturn(mockedAttributeMap);
        when(mockedAttributeMap.get(SOURCE_ATTRIBUTE_ID)).thenReturn(resolvedAttributeDefinition);
        when(mockedIdPAttribute.getValues()).thenReturn(idPAttributeValues);

        exception.expect(ResolutionException.class);
        exception.expectMessage("Value 'uid' could not be resolved");

        sharedTokenDataConnector.doDataConnectorResolve(mockAttributeResolutionContext,
                mockAttributeResolverWorkContext);
    }

    @Test
    public void ensureResolutionExceptionIsThrownIfSourceAttributeDoesNotResolveAsString() throws ResolutionException {

        @SuppressWarnings(UNCHECKED)
        Map<String, ResolvedAttributeDefinition> mockedAttributeMap = mock(Map.class);

        AttributeDefinition mockedAttributeDefinition = mock(AttributeDefinition.class);
        IdPAttribute mockedIdPAttribute = mock(IdPAttribute.class);
        when(mockedAttributeDefinition.isInitialized()).thenReturn(true);
        when(mockedAttributeDefinition.isDestroyed()).thenReturn(false);
        ResolvedAttributeDefinition resolvedAttributeDefinition = new ResolvedAttributeDefinition(
                mockedAttributeDefinition, mockedIdPAttribute);

        Set<Map.Entry<String, ResolvedAttributeDefinition>> entrySet = new HashSet<>();
        entrySet.add(new AbstractMap.SimpleEntry<>(SOURCE_ATTRIBUTE_ID, resolvedAttributeDefinition));

        List<IdPAttributeValue<?>> idPAttributeValues = new ArrayList<>();
        idPAttributeValues.add(new ByteAttributeValue(new byte[1]));

        when(mockAttributeResolverWorkContext.getResolvedIdPAttributeDefinitions()).thenReturn(mockedAttributeMap);
        when(mockedAttributeMap.entrySet()).thenReturn(entrySet);
        when(mockedAttributeMap.get(SOURCE_ATTRIBUTE_ID)).thenReturn(resolvedAttributeDefinition);
        when(mockedIdPAttribute.getValues()).thenReturn(idPAttributeValues);

        exception.expect(ResolutionException.class);
        exception.expectMessage("Value 'uid' must resolve to a String");

        sharedTokenDataConnector.doDataConnectorResolve(mockAttributeResolutionContext,
                mockAttributeResolverWorkContext);

    }

    @Test
    public void ensureResolutionExceptionIsThrownIfSourceAttributeIsNull() throws ResolutionException {

        @SuppressWarnings(UNCHECKED)
        Map<String, ResolvedAttributeDefinition> mockedAttributeMap = mock(Map.class);

        when(mockAttributeResolverWorkContext.getResolvedIdPAttributeDefinitions()).thenReturn(mockedAttributeMap);
        when(mockedAttributeMap.get(SOURCE_ATTRIBUTE_ID)).thenReturn(null);

        exception.expect(ResolutionException.class);
        exception.expectMessage("Value 'uid' could not be resolved");

        sharedTokenDataConnector.doDataConnectorResolve(mockAttributeResolutionContext,
                mockAttributeResolverWorkContext);

    }


}

