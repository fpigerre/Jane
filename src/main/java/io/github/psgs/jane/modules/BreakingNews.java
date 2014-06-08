package io.github.psgs.jane.modules;

import io.github.psgs.jane.Jane;
import io.github.psgs.jane.utilities.AudioUtils;
import io.github.psgs.jane.utilities.BrowserUtils;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class BreakingNews extends Module {

    public static List<String> acceptedInput = new ArrayList<String>();
    static Twitter twitter = ReadTweet.getTwitterInstance();

    public BreakingNews() {
        super("BreakingNews", "Reads breaking news from http://twitter.com/BreakingNews", 1, 3);
        acceptedInput.add("get");
        acceptedInput.add("read");
        acceptedInput.add("breaking");
        acceptedInput.add("news");
    }

    public static void main(String[] args) {
        if (Jane.twitterUsed) {
            ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);
            ses.scheduleWithFixedDelay(new Runnable() {
                @Override
                public void run() {
                    execute();
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }, 0, 5, TimeUnit.MINUTES);
        }
    }

    public static void execute() {
        if (Jane.twitterUsed) {
            try {
                List<Status> statuses = twitter.getUserTimeline("BreakingNews");
                Date date = new Date(System.currentTimeMillis() - 305000);
                for (Status status : statuses) {
                    if (status.getCreatedAt().after(date)) {
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
                if (BrowserUtils.isConnected()) ex.printStackTrace();
            }
        }
    }

    @Override
    public List<String> getAcceptedInput() {
        return acceptedInput;
    }
}
