package io.github.psgs.jane.modules;

import io.github.psgs.jane.utilities.AudioUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class GoogleSearch extends Module {

    public static List<String> acceptedInput = new ArrayList<String>();

    public GoogleSearch() {
        super("Google Search", "A module that returns Google results", 3, 1);
        acceptedInput.add("search");
        acceptedInput.add("google");
    }

    /**
     * Searches Google for results matching a certain string
     *
     * @param search Criteria to restrict the Google search to
     */
    public static void execute(String search) {
        String google = "https://www.google.com/search?q=";
        String charset = "UTF-8";
        String userAgent = "Jane 1.0 (+http://psgs.tk)"; // Company name and bot homepage

        try {
            int resultCount = 0;
            Elements links = Jsoup.connect(google + URLEncoder.encode(search, charset)).userAgent(userAgent).get().select("li.g>h3>a");
            for (Element link : links) {
                if (resultCount < 5) {
                    String title = link.text();
                    String url = link.absUrl("href"); // Google returns URLs in format "http://www.google.com/url?q=<url>&sa=U&ei=<someKey>".
                    url = URLDecoder.decode(url.substring(url.indexOf('=') + 1, url.indexOf('&')), "UTF-8");

                    if (!url.startsWith("http") && !url.startsWith("https")) {
                        continue; // Ads/news/etc.
                    }

                    resultCount++;
                    System.out.println("Title: " + title);
                    System.out.println("URL: " + url);
                    AudioUtils.talk(title);
                }
            }
        } catch (IOException ex) {
            System.err.println("An error occurred while attempting to search Google!");
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<String> getAcceptedInput() {
        return acceptedInput;
    }
}
