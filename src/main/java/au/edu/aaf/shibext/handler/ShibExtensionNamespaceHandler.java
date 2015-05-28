package au.edu.aaf.shibext.handler;

import au.edu.aaf.shibext.sharedtoken.SharedTokenDataConnectorBeanDefinitionParser;
import net.shibboleth.ext.spring.util.BaseSpringNamespaceHandler;

/**
 * Created by rianniello on 28/05/15.
 */
public class ShibExtensionNamespaceHandler extends BaseSpringNamespaceHandler {
    public static final java.lang.String NAMESPACE = "urn:mace:aaf.edu.au:shibboleth:2.0:resolver:dc";

    /**
     * Invoked by the {@link DefaultBeanDefinitionDocumentReader} after
     * construction but before any custom elements are parsed.
     *
     * @see NamespaceHandlerSupport#registerBeanDefinitionParser(String, BeanDefinitionParser)
     */
    @Override
    public void init() {
        registerBeanDefinitionParser(
                SharedTokenDataConnectorBeanDefinitionParser.TYPE_NAME,
                new SharedTokenDataConnectorBeanDefinitionParser());


    }
}
