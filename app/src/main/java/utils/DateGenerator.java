package utils;

import java.util.Random;

public class DateGenerator {
    private int day;
    private int month;
    private int year;

    private Random rand = new Random();
    private static int monthDays[] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private static int centuryDays[] = {5, 3, 2, 0};
    private static int doomsdays[] = {3, 28, 14, 4, 9, 6, 11, 8, 5, 10, 7, 12};
    private static String months[] = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

    public DateGenerator(String difficulty) {
        switch (difficulty) {
            case "easy":
                while (!validDate(day, month, year)) {
                    setYear();
                    setMonth();
                    while (!easyDay()) {
                        setDay();
                    }
                }
                break;
            case "normal":
                while (!validDate(day, month, year)) {
                    setYear();
                    setMonth();
                    setDay();
                }
                break;
            default:
                break;
        }
    }

    private static boolean leapYear(int year) {
        return ((year % 400) != 0) && ((year % 4) == 0);
    }

    private static boolean validDate(int day, int month, int year) {
        if (month < 1 || month > 12)
            return false;
        if (day < 1)
            return false;
        if (month == 2) {
            if (leapYear(year)) {
                if (day > 29)
                    return false;
            } else {
                if (day > 28)
                    return false;
            }
        } else {
            if (day > monthDays[month - 1])
                return false;
        }
        return true;
    }

    public int getDayOfWeek() {
        if (!validDate(day, month, year))
            return -1;

        int centuryDay = centuryDays[((year / 100) - 14) % 4];
        boolean isLeapYear = leapYear(year);

        year = year % 100;
        int anchorDay = year / 12 + (year % 12) + (year % 12) / 4;

        int doomsday = doomsdays[month - 1];
        if (isLeapYear && month < 3)
            doomsday++;

        return (centuryDay + anchorDay + day - doomsday + 35) % 7;
    }

    public String getWeekday(int dayOfWeek) {
        switch (dayOfWeek) {
            case 0: return "Sunday";
            case 1: return "Monday";
            case 2: return "Tuesday";
            case 3: return "Wednesday";
            case 4: return "Thursday";
            case 5: return "Friday";
            case 6: return "Saturday";
            default: return "Error";
        }
    }

    private boolean easyDay() {
        if ((month == 1 && (!leapYear(year))) || month == 10) {
            if (day == 3 || day == 10 || day == 17 || day == 24 || day == 31) {
                return true;
            }
        } else if ((month == 1 && leapYear(year)) || month == 4 || month == 7) {
            if (day == 4 || day == 11 || day == 18 || day == 25) {
                return true;
            }
        } else if ((month == 2 && !leapYear(year)) || month == 3 || month == 11) {
            if (day == 7 || day == 14 || day == 21 || day == 28) {
                return true;
            }
        } else if ((month == 2 && leapYear(year)) || month == 8) {
            if (day == 1 || day == 8 || day == 15 || day == 22 || day == 29) {
                return true;
            }
        } else if (month == 5) {
            if (day == 2 || day == 9 || day == 16 || day == 23 || day == 30) {
                return true;
            }
        } else if (month == 6) {
            if (day == 6 || day == 13 || day == 20 || day == 27) {
                return true;
            }
        } else if ((month == 9 || month == 12) && (day == 5 || day ==  12 || day == 19 || day == 26)) {
                return true;
            }
        return false;
    }

    private void setDay() {
        this.day = rand.nextInt(31) + 1;
    }

    private void setMonth() {
        this.month = rand.nextInt(12) + 1;
    }

    private void setYear() {
        this.year = rand.nextInt(2199 + 1 - 1800) + 1800;
    }

    public int getMonth() {
        return this.month;
    }

    public int getYear() {
        return this.year;
    }

    public String toFormat(String dateFormat) {
        switch (dateFormat) {
            case "uk_alpha":
                return day + " " + months[month - 1] + " " + year;
            case "us_alpha":
                return months[month - 1] + " " + day + " " + year;
            case "uk_num":
                return day + "/" + month + "/" + year;
            case "us_num":
                return month + "/" + day + "/" + year;
            default:
                return day + "/" + month + "/" + year;
        }
    }
}