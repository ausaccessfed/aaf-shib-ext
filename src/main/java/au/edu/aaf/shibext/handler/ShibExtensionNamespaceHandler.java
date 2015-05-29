package au.edu.aaf.shibext.handler;

import au.edu.aaf.shibext.sharedtoken
        .SharedTokenDataConnectorBeanDefinitionParser;
import net.shibboleth.ext.spring.util.BaseSpringNamespaceHandler;


/**
 *
 */
public class ShibExtensionNamespaceHandler extends BaseSpringNamespaceHandler {
    public static final java.lang.String NAMESPACE = "urn:mace:aaf.edu" +
            ".au:shibboleth:2.0:resolver:dc";

    @Override
    public void init() {
        registerBeanDefinitionParser(
                SharedTokenDataConnectorBeanDefinitionParser.TYPE_NAME,
                new SharedTokenDataConnectorBeanDefinitionParser());


    }
}
