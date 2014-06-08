import io.github.psgs.jane.interfaces.SystemTrayIcon;
import org.junit.After;
import org.junit.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.mockito.Mockito.validateMockitoUsage;

public class TrayIconTest {

    @Test
    public void initiateTrayIcon() {
        // Check whether we're running on Linux
        if (!new File("/usr/lib").exists()) {
            SystemTrayIcon trayIcon = new SystemTrayIcon();
        }
    }

    @After
    public void validate() {
        validateMockitoUsage();
    }
}
