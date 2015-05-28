package au.edu.aaf.shibext.sharedtoken;

import au.edu.aaf.shibext.handler.ShibExtensionNamespaceHandler;
import net.shibboleth.idp.attribute.resolver.AbstractDataConnector;
import net.shibboleth.idp.attribute.resolver.spring.dc.AbstractDataConnectorParser;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSimpleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;

import javax.xml.bind.Element;
import javax.xml.namespace.QName;

/**
 * Created by rianniello on 28/05/15.
 */
public class SharedTokenDataConnectorBeanDefinitionParser extends AbstractDataConnectorParser {

    /**
     * Schema type.
     */
    public static final QName SCHEMA_TYPE = new QName(ShibExtensionNamespaceHandler.NAMESPACE, "SharedToken");

    /**
     * Schema type name.
     */
    public static final QName TYPE_NAME = new QName(ShibExtensionNamespaceHandler.NAMESPACE, "SharedToken");


    /**
     * Per parser indication of what we are building.
     *
     * @return the class
     */
    @Override
    protected Class<? extends AbstractDataConnector> getNativeBeanClass() {
        return SharedTokenDataConnector.class;
    }

    /** {@inheritDoc} */
    @Override
    protected void doV2Parse(org.w3c.dom.Element pluginConfig,
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
