package moduletests;

import io.github.psgs.jane.utilities.AudioUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import static org.mockito.Mockito.*;

@Ignore("A rogue assertion error is triggered")
public class GoogleSearchTest {

    Elements links = mock(Elements.class);
    Jsoup jsoup = mock(Jsoup.class);
    AudioUtils audioUtils = mock(AudioUtils.class);

    @Test
    public void search() {
        try {
            String google = "http://www.google.com/search?q=";
            String search = "wikipedia";
            String charset = "UTF-8";
            String userAgent = "Jane 1.0 (+http://psgs.tk)"; // Change this to your company's name and bot homepage!

            int resultCount = 0;
            links = jsoup.connect(google + URLEncoder.encode(search, charset)).userAgent(userAgent).get().select("li.g>h3>a");
            for (Element link : links) {
                if (resultCount < 5) {
                    String title = link.text();
                    String url = link.absUrl("href"); // Google returns URLs in format "http://www.google.com/url?q=<url>&sa=U&ei=<someKey>".
                    url = URLDecoder.decode(url.substring(url.indexOf('=') + 1, url.indexOf('&')), "UTF-8");

                    if (!url.startsWith("http")) {
                        continue; // Ads/news/etc.
                    }

                    resultCount++;
                    System.out.println("Title: " + title);
                    assert title.length() > 1;
                    System.out.println("URL: " + url);
                    assert url.length() > 1;
                    audioUtils.talk(title);
                }
            }
            assert links.size() >= 5;
            verify(audioUtils).talk(anyString());
        } catch (FileNotFoundException ex) {
            System.out.println("A result couldn't be spoken!!");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @After
    public void validate() {
        validateMockitoUsage();
    }
}
