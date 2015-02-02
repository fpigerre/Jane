package io.github.psgs.jane.modules;

import io.github.psgs.jane.utilities.AudioUtils;
import io.github.psgs.jane.utilities.JsonUtils;
import io.github.psgs.jane.utilities.Settings;
import io.github.psgs.jane.utilities.StringUtils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GitHubJobs extends Module {

    public GitHubJobs() {
        super("GitHubJobs", "Returns a list of applicable GitHub Jobs", 0);
    }

    /**
     * Schedules a task to check for new jobs every 5 minutes
     *
     * @param args System arguments
     */
    public static void main(String[] args) {
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

    /**
     * Checks for new GitHub jobs matching a certain criteria
     *
     * @param input Criteria to restrict the job search to
     */
    public static void execute(String input) {

        // TODO: Pass location from voice to module

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("http://jobs.github.com/positions.json?");
        if (input.contains("location") || input.contains("near") || input.contains("at")) {
            input.replace(" location ", "");
            input.replace(" near ", "");
            input.replace(" at ", "");
            stringBuilder.append("&location=" + StringUtils.stripStringFromArray(input, StringUtils.nonDeterministicWords));
        }
        if (input.contains("full") || input.contains("time")) {
            input.replace(" full time ", "");
            input.replace(" full ", "");
            input.replace(" time ", "");
            stringBuilder.append("&full_time=true");
        }
        stringBuilder.append("&description=" + input);

        try {
            for (JsonUtils.Job job : JsonUtils.getJobList(stringBuilder.toString())) {
                AudioUtils.talk(job.company + " is seeking a " + job.title + " for a " + job.type + " position in " + job.location);
            }
        } catch (Exception ex) {
            System.out.println("An error occurred while trying to fetch JSON data!");
        }
    }

    /**
     * Executes a default GitHub job search, using parameters defined in settings.properties
     */
    public static void execute() {
        try {
            for (JsonUtils.Job job : JsonUtils.getJobList("http://jobs.github.com/positions.json?&location=" + Settings.location)) {
                AudioUtils.talk(job.company + " is seeking a " + job.title + " for a " + job.type + " position in " + job.location);
            }
        } catch (Exception ex) {
            System.out.println("An error occurred while trying to fetch JSON data!");
        }
    }
}
