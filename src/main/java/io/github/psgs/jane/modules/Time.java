package io.github.psgs.jane.modules;

import io.github.psgs.jane.utilities.AudioUtils;
import io.github.psgs.jane.utilities.StringUtils;
import io.github.psgs.jane.utilities.TimeUtils;

import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Time extends Module {

    public static List<String> acceptedInput = new ArrayList<String>();

    public Time() {
        super("Time", "Tells the user the current time", 0, 4);
        acceptedInput.add("get");
        acceptedInput.add("whats");
        acceptedInput.add("what");
        acceptedInput.add("what's");
        acceptedInput.add("is");
        acceptedInput.add("time");
    }

    /**
     * Speaks the current time
     */
    public static void execute() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy/HH/mm/ss");
        Date date = new Date();
        String[] time = dateFormat.format(date).split("/");
        String[] monthInt = time[1].split("0");
        String month;
        // Checks if the month is formatted with a '0' at the start
        if (monthInt[0].equals("0")) {
            month = TimeUtils.getMonthFromInt(Integer.parseInt(monthInt[1]));
        } else {
            month = TimeUtils.getMonthFromInt(Integer.parseInt(time[1]));
        }
        try {
            // A suffix for the day is necessary for the day to sound correct
            AudioUtils.talk("It is currently the " + StringUtils.attachSuffix(Integer.parseInt(time[0])) + " of " + month + ", " + time[2]);
        } catch (FileNotFoundException ex) {
            System.out.println("A result couldn't be spoken!!");
        }
    }

    @Override
    public List<String> getAcceptedInput() {
        return acceptedInput;
    }
}
