package moduletests;

import io.github.psgs.jane.modules.ReadTweet;
import io.github.psgs.jane.utilities.AudioUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

import java.io.*;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.validateMockitoUsage;

public class BreakingNewsTest {

    // Whether Twitter keys are loaded correctly
    static boolean validAccessToken = false;
    static boolean validAccessTokenSecret = false;

    /**
     * Attempts to find Twitter keys contained in the twitter4j.properties file and load them
     */
    @Before
    public void refreshTwitterKeys() {
        try {
            if (new File("twitter4j.properties").exists()) {
                BufferedReader bufferedReader = new BufferedReader(new FileReader("twitter4j.properties"));
                StringBuilder stringBuilder = new StringBuilder();
                String line = bufferedReader.readLine();

                while (line != null) {
                    if (line.contains("oauth.accessToken=")) {
                        String rawToken = line.replace("oauth.accessToken=", "");
                        if (!rawToken.equals("") && !rawToken.equals(" ") && rawToken != null) {
                            validAccessToken = true;
                        }
                    }
                    if (line.contains("oauth.accessTokenSecret=")) {
                        String rawTokenSecret = line.replace("oauth.accessTokenSecret=", "");
                        if (!rawTokenSecret.equals(" ") && rawTokenSecret.equals("") && rawTokenSecret != null) {
                            validAccessTokenSecret = true;
                        }
                    }
                    stringBuilder.append(line);
                    stringBuilder.append(System.lineSeparator());
                    line = bufferedReader.readLine();
                }
            } else {
                validAccessToken = false;
                validAccessTokenSecret = false;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void readBreakingTweet() {
        if (validAccessToken && validAccessTokenSecret) {
            Twitter twitter = TwitterFactory.getSingleton();
            try {
                List<Status> statuses = twitter.getUserTimeline("BreakingNews");
                Date date = new Date(System.currentTimeMillis() - 305000);
                for (Status status : statuses) {
                    if (status.getCreatedAt().after(date)) {
                        assert status.getId() != 0 && !status.getText().equals("");
                        ReadTweet.execute(status);
                    }
                }
            } catch (TwitterException ex) {
                System.out.println("An error occurred while trying to fetch a tweet!");
                if (ex.getErrorCode() == 34) {
                    System.out.println("The tweet you requested wasn't found!");
                    try {
                        AudioUtils.talk("I tried to access a tweet, but it wasn't found.");
                    } catch (FileNotFoundException exception) {
                        System.out.println("A result couldn't be spoken!!");
                    }
                }
                ex.printStackTrace();
            }
        }
    }

    @After
    public void validate() {
        validAccessToken = false;
        validAccessTokenSecret = false;
        validateMockitoUsage();
    }
}
