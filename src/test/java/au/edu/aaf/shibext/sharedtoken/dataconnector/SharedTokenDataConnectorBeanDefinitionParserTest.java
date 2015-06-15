package au.edu.aaf.shibext.sharedtoken.dataconnector;

import au.edu.aaf.shibext.sharedtoken.dataconnector.SharedTokenDataConnector;
import au.edu.aaf.shibext.sharedtoken.dataconnector.SharedTokenDataConnectorBeanDefinitionParser;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class SharedTokenDataConnectorBeanDefinitionParserTest {

    private static final String SOURCE_ATTRIBUTE_ID = "uid";
    private static final String GENERATED_ATTRIBUTE_ID = "auEduPersonSharedToken";
    private static final String SALT = "Ez8m1HDSLBxu0JNcPEywmOpy+apq4Niw9kEMmAyWbhJqcfAb";
    private static final String DATA_SOURCE = "DS_idp_admin";

    private SharedTokenDataConnectorBeanDefinitionParser sharedTokenDataConnectorBeanDefinitionParser;

    @Before
    public void setup() {
        sharedTokenDataConnectorBeanDefinitionParser = new SharedTokenDataConnectorBeanDefinitionParser();
    }

    @Test
    public void testGetNativeBeanClass() {
        assertThat(sharedTokenDataConnectorBeanDefinitionParser.getNativeBeanClass(),
                is(equalTo(SharedTokenDataConnector.class)));
    }

    @Test
    public void ensureThatV2ParseAddsPropertyValuesAsExpected() {
        Element mockPluginConfig = mock(Element.class);
        ParserContext mockParserContext = null;
        BeanDefinitionBuilder mockPluginBuilder = mock(BeanDefinitionBuilder.class);

        when(mockPluginConfig.getAttributeNS(null, "sourceAttributeId")).thenReturn(SOURCE_ATTRIBUTE_ID);
        when(mockPluginConfig.getAttributeNS(null, "salt")).thenReturn(SALT);
        when(mockPluginConfig.getAttributeNS(null, "dataSource")).thenReturn(DATA_SOURCE);

        sharedTokenDataConnectorBeanDefinitionParser.doV2Parse(
                mockPluginConfig, mockParserContext, mockPluginBuilder);

        verify(mockPluginBuilder).addPropertyValue("generatedAttributeId", GENERATED_ATTRIBUTE_ID);
        verify(mockPluginBuilder).addPropertyValue("sourceAttributeId", SOURCE_ATTRIBUTE_ID);
        verify(mockPluginBuilder).addPropertyValue("salt", SALT);
        verify(mockPluginBuilder).addPropertyValue("dataSource", DATA_SOURCE);
    }

}

