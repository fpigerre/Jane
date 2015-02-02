package io.github.psgs.jane.utilities;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    /**
     * Returns data as a String from a URL
     *
     * @param urlString A URL to fetch data from
     * @return Data, formatted as a String
     * @throws Exception
     */
    public static String readUrl(String urlString) throws Exception {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);

            return buffer.toString();
        } finally {
            if (reader != null)
                reader.close();
        }
    }

    /**
     * Pulls JSON data from a URL and formats it as a Page object
     *
     * @param url A URL to fetch JSON data from
     * @return A Page object
     * @throws Exception
     */
    public static Page getGsonPage(String url) throws Exception {
        String json = readUrl(url);
        Gson gson = new Gson();
        return gson.fromJson(json, Page.class);
    }

    /**
     * Returns a list of Job objects from a URL that contains JSON data
     *
     * @param url A URL to fetch JSON data from
     * @return An ArrayList of Job objects
     * @throws Exception
     */
    public static List<Job> getJobList(String url) throws Exception {
        String json = readUrl(url);
        Gson gson = new Gson();
        JobPage page = gson.fromJson(json, JobPage.class);
        List<Job> jobList = new ArrayList<Job>();
        for (Job job : page.jobs) {
            jobList.add(job);
        }
        return jobList;
    }

    /**
     * A JSON 'Item' object
     */
    public static class Item {
        String title;
        String link;
        String description;
    }

    /**
     * A JSON 'Page' object
     */
    static class Page {
        String title;
        String link;
        String description;
        String language;
        List<Item> items;
    }

    /**
     * A JSON 'Page' object that contains a number of Job objects
     */
    static class JobPage {
        List<Job> jobs;
    }

    /**
     * A JSON 'Job' object
     */
    public static class Job {
        public String id;
        public String created_at;
        public String title;
        public String location;
        public String type;
        public String description;
        public String how_to_apply;
        public String company;
        public String company_url;
        public String company_logo;
        public String url;
    }
}
