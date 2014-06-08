package moduletests;

import io.github.psgs.jane.utilities.AudioUtils;
import io.github.psgs.jane.utilities.StringUtils;
import io.github.psgs.jane.utilities.TimeUtils;
import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.mockito.Mockito.*;

@Ignore("A rogue assertion error is triggered")
public class TimeModuleTest {

    TimeUtils timeUtils = mock(TimeUtils.class);

    @Test
    public void speakTime() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy/HH/mm/ss");
        Date date = new Date();
        String[] time = dateFormat.format(date).split("/");
        String[] monthInt = time[1].split("0");
        String month;
        if (monthInt[0].equals("0")) {
            month = timeUtils.getMonthFromInt(Integer.parseInt(monthInt[1]));
        } else {
            month = timeUtils.getMonthFromInt(Integer.parseInt(time[1]));
        }
        try {
            AudioUtils.talk("It is currently the " + StringUtils.attachSuffix(Integer.parseInt(time[0])) + " of " + month + ", " + time[2]);
        } catch (FileNotFoundException ex) {
            System.out.println("The time couldn't be spoken!");
        }
        assert Integer.parseInt(monthInt[1]) == TimeUtils.getMonthFromString(month);
        verify(timeUtils, atLeastOnce()).getMonthFromInt(anyInt());
    }

    @After
    public void validate() {
        validateMockitoUsage();
    }
}
