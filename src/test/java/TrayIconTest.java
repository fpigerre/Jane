import io.github.psgs.jane.interfaces.SystemTrayIcon;
import org.junit.After;
import org.junit.Test;

import java.io.File;

import static org.mockito.Mockito.validateMockitoUsage;

public class TrayIconTest {

    @Test
    public void initiateTrayIcon() {
        // Check whether we're running on Linux
        if (!new File("/tmp/Jane.lock").exists()) {
            SystemTrayIcon trayIcon = new SystemTrayIcon();
        }
    }

    @After
    public void validate() {
        validateMockitoUsage();
    }
}
