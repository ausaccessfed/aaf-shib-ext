package au.edu.aaf.shibext.sharedtoken;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class SharedTokenDataConnectorBeanDefinitionParserTest {

    @InjectMocks
    private SharedTokenDataConnectorBeanDefinitionParser sharedTokenDataConnectorBeanDefinitionParser;

    @Test
    public void testGetNativeBeanClass() {
        assertThat(sharedTokenDataConnectorBeanDefinitionParser.getNativeBeanClass(),
                is(equalTo(SharedTokenDataConnector.class)));
    }

    @Test
    public void testDoV2ParseWithNoExistingNamespace() {
        Element mockPluginConfig = mock(Element.class);
        ParserContext mockParserContext = null;
        BeanDefinitionBuilder mockPluginBuilder = mock(BeanDefinitionBuilder.class);

        sharedTokenDataConnectorBeanDefinitionParser.doV2Parse(
                mockPluginConfig, mockParserContext, mockPluginBuilder);

        verify(mockPluginBuilder).addPropertyValue("generatedAttributeId", "auEduPersonSharedToken");
    }

    @Test
    public void testDoV2ParseWithExistingNamespace() {
        Element mockPluginConfig = mock(Element.class);
        ParserContext mockParserContext = null;
        BeanDefinitionBuilder mockPluginBuilder = mock(BeanDefinitionBuilder.class);

        when(mockPluginConfig.hasAttributeNS(null, "generatedAttributeID")).thenReturn(true);

        sharedTokenDataConnectorBeanDefinitionParser.doV2Parse(
                mockPluginConfig, mockParserContext, mockPluginBuilder);

        verify(mockPluginBuilder).addPropertyValue("generatedAttributeId", null);
    }

    public SharedTokenDataConnectorBeanDefinitionParser getSharedTokenDataConnectorBeanDefinitionParser() {
        return sharedTokenDataConnectorBeanDefinitionParser;
    }

    public void setSharedTokenDataConnectorBeanDefinitionParser(SharedTokenDataConnectorBeanDefinitionParser
                                                                        sharedTokenDataConnectorBeanDefinitionParser) {
        this.sharedTokenDataConnectorBeanDefinitionParser = sharedTokenDataConnectorBeanDefinitionParser;
    }
}

