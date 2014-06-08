package moduletests;

import io.github.psgs.jane.modules.ReadTweet;
import io.github.psgs.jane.utilities.AudioUtils;
import org.junit.After;
import org.junit.Test;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

import java.io.FileNotFoundException;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.validateMockitoUsage;

public class BreakingNewsTest {

    @Test
    public void readBreakingTweet() {
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

    @After
    public void validate() {
        validateMockitoUsage();
    }
}
