package au.edu.aaf.shibext.handler;

import org.junit.Before;
import org.junit.Test;

public class ShibExtensionNamespaceHandlerTest {

    private ShibExtensionNamespaceHandler shibExtensionNamespaceHandler;

    @Before
    public void setup() {
        shibExtensionNamespaceHandler = new ShibExtensionNamespaceHandler();
    }

    @Test
    public void testInit() {
        shibExtensionNamespaceHandler.init();
    }

}
