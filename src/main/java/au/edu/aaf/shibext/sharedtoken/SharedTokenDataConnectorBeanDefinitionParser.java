package au.edu.aaf.shibext.sharedtoken;

import au.edu.aaf.shibext.handler.ShibExtensionNamespaceHandler;
import net.shibboleth.idp.attribute.resolver.AbstractDataConnector;
import net.shibboleth.idp.attribute.resolver.spring.dc.AbstractDataConnectorParser;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

import javax.xml.namespace.QName;


/**
 *
 */
public class SharedTokenDataConnectorBeanDefinitionParser extends
        AbstractDataConnectorParser {

    public static final QName SCHEMA_TYPE = new QName(ShibExtensionNamespaceHandler.NAMESPACE, "SharedToken");

    public static final QName TYPE_NAME = new QName(ShibExtensionNamespaceHandler.NAMESPACE, "SharedToken");

    @Override
    protected Class<? extends AbstractDataConnector> getNativeBeanClass() {
        return SharedTokenDataConnector.class;
    }

    @Override
    protected void doV2Parse(Element pluginConfig,
                             ParserContext parserContext,
                             BeanDefinitionBuilder pluginBuilder) {

        if (pluginConfig.hasAttributeNS(null, "generatedAttributeID")) {
            pluginBuilder.addPropertyValue("generatedAttributeId", pluginConfig
                    .getAttributeNS(null, "generatedAttributeID"));
        } else {
            pluginBuilder.addPropertyValue("generatedAttributeId",
                    "auEduPersonSharedToken");
        }
    }
}
