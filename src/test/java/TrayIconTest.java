import io.github.psgs.jane.interfaces.SystemTrayIcon;
import org.junit.After;
import org.junit.Test;

import static org.mockito.Mockito.validateMockitoUsage;

public class TrayIconTest {

    @Test
    public void initiateTrayIcon() {
        SystemTrayIcon trayIcon = new SystemTrayIcon();
    }

    @After
    public void validate() {
        validateMockitoUsage();
    }
}
