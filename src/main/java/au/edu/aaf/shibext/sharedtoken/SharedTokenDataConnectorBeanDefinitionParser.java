package au.edu.aaf.shibext.sharedtoken;

import au.edu.aaf.shibext.handler.ShibExtensionNamespaceHandler;
import net.shibboleth.idp.attribute.resolver.AbstractDataConnector;
import net.shibboleth.idp.attribute.resolver.spring.dc.AbstractDataConnectorParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

import javax.xml.namespace.QName;


/**
 * Facilitates the loading of values from xml configuration to SharedTokenDataConnector.
 *
 * @author rianniello
 * @see SharedTokenDataConnector
 */
public class SharedTokenDataConnectorBeanDefinitionParser extends AbstractDataConnectorParser {

    public static final QName TYPE_NAME = new QName(ShibExtensionNamespaceHandler.NAMESPACE, "SharedToken");

    private static final Logger LOG = LoggerFactory.getLogger(ShibExtensionNamespaceHandler.class);

    @Override
    protected Class<? extends AbstractDataConnector> getNativeBeanClass() {
        return SharedTokenDataConnector.class;
    }

    @Override
    protected void doV2Parse(Element pluginConfig, ParserContext parserContext, BeanDefinitionBuilder pluginBuilder) {
        LOG.trace("Started doV2Parse ...");
        pluginBuilder.addPropertyValue("generatedAttributeId", "auEduPersonSharedToken");
        pluginBuilder.addPropertyValue("sourceAttributeId", getSourceAttributeId(pluginConfig, "sourceAttributeId"));
        pluginBuilder.addPropertyValue("salt", getSourceAttributeId(pluginConfig, "salt"));
        pluginBuilder.addPropertyValue("idpIdentifier", getSourceAttributeId(pluginConfig, "idpIdentifier"));
    }

    private String getSourceAttributeId(Element pluginConfig, String attributeId) {
        return pluginConfig.getAttributeNS(null, attributeId);
    }
}
