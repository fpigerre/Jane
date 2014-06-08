package io.github.psgs.jane.modules;

import io.github.psgs.jane.utilities.AudioUtils;
import io.github.psgs.jane.utilities.JsonUtils;
import io.github.psgs.jane.utilities.Settings;
import io.github.psgs.jane.utilities.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GitHubJobs extends Module {

    public static List<String> acceptedInput = new ArrayList<String>();

    public GitHubJobs() {
        super("GitHubJobs", "Returns a list of applicable GitHub Jobs", 0, 3);
        acceptedInput.add("github");
        acceptedInput.add("jobs");
        acceptedInput.add("job");
        acceptedInput.add("get");
    }

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

    public static void execute(String rawInput) {
        String validatedInput = StringUtils.stripStringFromArray(rawInput, (String[]) acceptedInput.toArray());
        validatedInput.replace("jane", "");
        validatedInput.replace(".", "");

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("http://jobs.github.com/positions.json?");
        if (validatedInput.contains("location") || validatedInput.contains("near") || validatedInput.contains("at")) {
            validatedInput.replace(" location ", "");
            validatedInput.replace(" near ", "");
            validatedInput.replace(" at ", "");
            stringBuilder.append("&location=" + StringUtils.stripStringFromArray(validatedInput, StringUtils.nonDeterministicWords));
        }
        if (validatedInput.contains("full") || validatedInput.contains("time")) {
            validatedInput.replace(" full time ", "");
            validatedInput.replace(" full ", "");
            validatedInput.replace(" time ", "");
            stringBuilder.append("&full_time=true");
        }
        stringBuilder.append("&description=" + validatedInput);

        try {
            for (JsonUtils.Job job : JsonUtils.getJobList(stringBuilder.toString())) {
                AudioUtils.talk(job.company + " is seeking a " + job.title + " for a " + job.type + " position in " + job.location);
            }
        } catch (Exception ex) {
            System.out.println("An error occurred while trying to fetch JSON data!");
        }
    }

    public static void execute() {
        try {
            for (JsonUtils.Job job : JsonUtils.getJobList("http://jobs.github.com/positions.json?&location=" + Settings.location)) {
                AudioUtils.talk(job.company + " is seeking a " + job.title + " for a " + job.type + " position in " + job.location);
            }
        } catch (Exception ex) {
            System.out.println("An error occurred while trying to fetch JSON data!");
        }
    }

    @Override
    public List<String> getAcceptedInput() {
        return acceptedInput;
    }
}
