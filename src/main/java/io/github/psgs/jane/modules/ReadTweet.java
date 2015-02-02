package io.github.psgs.jane.modules;

import io.github.psgs.jane.Jane;
import io.github.psgs.jane.utilities.AudioUtils;
import io.github.psgs.jane.utilities.StringUtils;
import twitter4j.*;

import java.io.FileNotFoundException;
import java.util.Date;

public class ReadTweet extends Module {

    static Twitter twitter = TwitterFactory.getSingleton();

    public ReadTweet() {
        super("ReadTweet", "Reads/displays a tweet", 0);
    }

    /**
     * Reads a Tweet from a Tweet ID number
     *
     * @param tweetId     The ID of the tweet to read
     * @param textCommand An unused boolean to determine that the Tweet is being read using an ID number
     */
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

    /**
     * Reads a Tweet from a Status object
     *
     * @param status A Status object
     */
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

    /**
     * Returns the ReadTweet module's Twitter object instance
     *
     * @return An instance of a Twitter object
     */
    public static Twitter getTwitterInstance() {
        return twitter;
    }

    /**
     * Reads a Tweet from an Author
     *
     * @param author An author's username (without an @ symbol)
     */
    public static void execute(String author) {
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
                System.out.println("An error occurred while trying to fetch a tweet from an author!");
                ex.printStackTrace();
            }
        }
    }
}