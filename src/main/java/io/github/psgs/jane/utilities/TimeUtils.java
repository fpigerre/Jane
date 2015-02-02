package io.github.psgs.jane.utilities;

public class TimeUtils {

    /**
     * Gets the name of a month from the numerical value of the month
     *
     * @param month The numerical value of a month
     * @return The name of the month
     */
    public static String getMonthFromInt(int month) {
        if (month <= 12) {
            switch (month) {
                case 1:
                    return "january";
                case 2:
                    return "february";
                case 3:
                    return "march";
                case 4:
                    return "april";
                case 5:
                    return "may";
                case 6:
                    return "june";
                case 7:
                    return "july";
                case 8:
                    return "august";
                case 9:
                    return "september";
                case 10:
                    return "october";
                case 11:
                    return "november";
                case 12:
                    return "december";
            }
        }
        return "";
    }

    /**
     * Get the numerical value of a month (1-12) from the name of a month
     *
     * @param month The name of a month
     * @return The numerical value of the month
     */
    public static int getMonthFromString(String month) {
        if (month.equals("january")) return 1;
        if (month.equals("february")) return 2;
        if (month.equals("march")) return 3;
        if (month.equals("april")) return 4;
        if (month.equals("may")) return 5;
        if (month.equals("june")) return 6;
        if (month.equals("july")) return 7;
        if (month.equals("august")) return 8;
        if (month.equals("september")) return 9;
        if (month.equals("october")) return 10;
        if (month.equals("november")) return 11;
        if (month.equals("december")) return 12;
        return 0;
    }
}
