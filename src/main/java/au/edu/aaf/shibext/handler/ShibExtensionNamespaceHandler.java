package au.edu.aaf.shibext.handler;

import au.edu.aaf.shibext.sharedtoken.SharedTokenDataConnectorBeanDefinitionParser;
import net.shibboleth.ext.spring.util.BaseSpringNamespaceHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class ShibExtensionNamespaceHandler extends BaseSpringNamespaceHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ShibExtensionNamespaceHandler.class);
    public static final String NAMESPACE = "urn:mace:aaf.edu.au:shibboleth:2.0:resolver:dc";

    @Override
    public void init() {
        LOG.debug("Registering SharedTokenDataConnectorBeanDefinitionParser...");
        registerBeanDefinitionParser(
                SharedTokenDataConnectorBeanDefinitionParser.TYPE_NAME,
                new SharedTokenDataConnectorBeanDefinitionParser());
    }
}
