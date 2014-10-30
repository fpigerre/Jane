package utilitytests;

import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.mockito.Mockito.validateMockitoUsage;

@Ignore("A rogue assertion error is triggered")
public class ConnectionTest {

    @Test
    public void execute() {
        System.out.println(isConnected());
        assert isConnected();
    }

    public boolean isConnected() {
        try {
            URL url = new URL("https://google.com");
            HttpURLConnection urlConnect = (HttpURLConnection) url.openConnection();
            return urlConnect.getResponseCode() == 200;
        } catch (IOException ex) {
            return false;
        }
    }

    @After
    public void validate() {
        validateMockitoUsage();
    }
}
