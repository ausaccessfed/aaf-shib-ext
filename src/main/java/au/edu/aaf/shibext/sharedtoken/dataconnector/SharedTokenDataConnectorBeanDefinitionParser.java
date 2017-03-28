package au.edu.aaf.shibext.sharedtoken.dataconnector;

import au.edu.aaf.shibext.handler.ShibExtensionNamespaceHandler;
import au.edu.aaf.shibext.sharedtoken.datasource.DataSourceResolver;
import net.shibboleth.idp.attribute.resolver.AbstractDataConnector;
import net.shibboleth.idp.attribute.resolver.spring.dc.impl.AbstractDataConnectorParser;
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

    /**
     * Used for resolution of a javax.sql.DataSource.
     */
    private DataSourceResolver dataSourceResolver = new DataSourceResolver();

    @Override
    protected Class<? extends AbstractDataConnector> getNativeBeanClass() {
        return SharedTokenDataConnector.class;
    }

    @Override
    protected void doV2Parse(Element pluginConfig, ParserContext parserContext, BeanDefinitionBuilder pluginBuilder) {
        pluginBuilder.addPropertyValue("generatedAttributeId", "auEduPersonSharedToken");
        pluginBuilder.addPropertyValue("sourceAttributeId", getAttribute(pluginConfig, "sourceAttributeId"));
        pluginBuilder.addPropertyValue("salt", getAttribute(pluginConfig, "salt"));
        pluginBuilder.addPropertyValue("primaryKeyName", getAttribute(pluginConfig, "primaryKeyName"));

        String dataSourceIdentifier = getAttribute(pluginConfig, "dataSource");
        pluginBuilder.addPropertyValue("dataSource", dataSourceResolver.lookup(dataSourceIdentifier));
    }

    private String getAttribute(Element pluginConfig, String attributeId) {
        return pluginConfig.getAttributeNS(null, attributeId);
    }
}
