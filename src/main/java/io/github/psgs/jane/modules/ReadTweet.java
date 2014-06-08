package io.github.psgs.jane.modules;

import io.github.psgs.jane.Jane;
import io.github.psgs.jane.utilities.AudioUtils;
import io.github.psgs.jane.utilities.StringUtils;
import twitter4j.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReadTweet extends Module {

    public static List<String> acceptedInput = new ArrayList<String>();
    static Twitter twitter = TwitterFactory.getSingleton();

    public ReadTweet() {
        super("ReadTweet", "Reads/displays a tweet", 0, 4);
        acceptedInput.add("get");
        acceptedInput.add("a");
        acceptedInput.add("tweet");
        acceptedInput.add("by");
        acceptedInput.add("read");
        acceptedInput.add("recent");
    }

    public static void execute(String tweetId, boolean textCommand) {
        if (Jane.twitterUsed) {
            try {
                Query query = new Query(tweetId);
                QueryResult result = twitter.search(query);
                for (Status status : result.getTweets()) {
                    String id = String.valueOf(status.getId());
                    if (id.equals(tweetId)) {
                        String statusText = status.getUser().getName() + " " + StringUtils.removeUrl(status.getText());
                        String output = statusText.replaceAll("#", "hashtag ");
                        System.out.println(output);
                        try {
                            if (output.length() > 100 && output.length() <= 200) {
                                AudioUtils.talk(output.substring(0, 100));
                                AudioUtils.talk(output.substring(100, output.length()));
                            } else if (output.length() > 200) {
                                AudioUtils.talk(output.substring(0, 100));
                                AudioUtils.talk(output.substring(100, 200));
                                AudioUtils.talk(output.substring(200, output.length()));
                            } else if (output.length() < 100) {
                                AudioUtils.talk(output);
                            }
                        } catch (FileNotFoundException ex) {
                            System.out.println("A result couldn't be spoken!!");
                        }
                    }
                }
            } catch (TwitterException ex) {
                System.out.println("An error occurred while trying to fetch a tweet from an ID!");
                ex.printStackTrace();
            }
        }
    }

    public static void execute(Status status) {
        if (Jane.twitterUsed) {
            String statusText = status.getUser().getName() + " " + StringUtils.removeUrl(status.getText());
            String output = statusText.replaceAll("#", "hashtag ");
            System.out.println(output);
            try {
                if (output.length() > 100 && output.length() <= 200) {
                    AudioUtils.talk(output.substring(0, 100));
                    AudioUtils.talk(output.substring(100, output.length()));
                } else if (output.length() > 200) {
                    AudioUtils.talk(output.substring(0, 100));
                    AudioUtils.talk(output.substring(100, 200));
                    AudioUtils.talk(output.substring(200, output.length()));
                } else if (output.length() < 100) {
                    AudioUtils.talk(output);
                }
            } catch (FileNotFoundException ex) {
                System.out.println("A result couldn't be spoken!!");
            }
        }
    }

    public static Twitter getTwitterInstance() {
        return twitter;
    }

    @Override
    public List<String> getAcceptedInput() {
        return acceptedInput;
    }

    public void execute(String author) {
        if (Jane.twitterUsed) {
            try {
                Query query = new Query("source:" + author);
                QueryResult result = twitter.search(query);
                for (Status status : result.getTweets()) {
                    Date date = new Date(System.currentTimeMillis() - 300000);
                    if (status.getCreatedAt().after(date)) {
                        String statusText = status.getUser().getName() + " " + StringUtils.removeUrl(status.getText());
                        String output = statusText.replaceAll("#", "hashtag ");
                        System.out.println(output);
                        try {
                            if (output.length() > 100 && output.length() <= 200) {
                                AudioUtils.talk(output.substring(0, 100));
                                AudioUtils.talk(output.substring(100, output.length()));
                            } else if (output.length() > 200) {
                                AudioUtils.talk(output.substring(0, 100));
                                AudioUtils.talk(output.substring(100, 200));
                                AudioUtils.talk(output.substring(200, output.length()));
                            } else if (output.length() < 100) {
                                AudioUtils.talk(output);
                            }
                        } catch (FileNotFoundException ex) {
                            System.out.println("A result couldn't be spoken!!");
                        }
                    }
                }
            } catch (TwitterException ex) {
                System.out.println("An error occurred while trying to fetch a tweet from an ID!");
                ex.printStackTrace();
            }
        }
    }
}