package moduletests;

import io.github.psgs.jane.utilities.AudioUtils;
import io.github.psgs.jane.utilities.JsonUtils;
import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;

import static org.mockito.Mockito.validateMockitoUsage;

public class GitHubJobsTest {

    @Ignore("JSON URL returns HTTP error 301")
    @Test
    public void getJobs() {
        String url = "http://jobs.github.com/positions.json?&location=america";
        try {
            for (JsonUtils.Job job : JsonUtils.getJobList(url)) {
                AudioUtils.talk(job.company + " is seeking a " + job.title + " for a " + job.type + " position in " + job.location);
            }
        } catch (Exception ex) {
            System.out.println("An error occurred while trying to fetch JSON data!");
            ex.printStackTrace();
        }
    }

    @After
    public void validate() {
        validateMockitoUsage();
    }
}
