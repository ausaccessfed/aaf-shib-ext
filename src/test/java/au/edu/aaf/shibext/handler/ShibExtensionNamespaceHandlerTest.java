package au.edu.aaf.shibext.handler;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ShibExtensionNamespaceHandlerTest {

    @InjectMocks
    private ShibExtensionNamespaceHandler shibExtensionNamespaceHandler;

    @Test
    public void testInit() {
        shibExtensionNamespaceHandler.init();
    }

    public ShibExtensionNamespaceHandler getShibExtensionNamespaceHandler() {
        return shibExtensionNamespaceHandler;
    }

    public void setShibExtensionNamespaceHandler(ShibExtensionNamespaceHandler shibExtensionNamespaceHandler) {
        this.shibExtensionNamespaceHandler = shibExtensionNamespaceHandler;
    }
}
