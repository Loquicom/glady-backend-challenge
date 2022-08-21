package com.challenge.gladybackend.config;

import lombok.extern.slf4j.Slf4j;

import java.util.Calendar;
import java.util.Date;

@Slf4j
public class TimeConfig {

    private static Calendar calendar;

    /**
     * Initialise the calendar use by other methods
     */
    private static void initCalendar() {
        if (calendar == null) {
            calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
        }
    }

    /**
     * The server time
     *
     * @param date The new server date
     */
    public static void setTime(Date date) {
        initCalendar();
        calendar.setTime(date);
    }

    /**
     * Set the server time
     *
     * @param year  New year
     * @param month New month (value between 1 and 12)
     * @param day   Day of thz month
     */
    public static void setTime(String year, String month, String day) {
        initCalendar();
        try {
            calendar.set(Calendar.YEAR, Integer.parseInt(year));
            calendar.set(Calendar.MONTH, Integer.parseInt(month) - 1);
            calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(day));
        } catch (NumberFormatException ex) {
            log.warn("Unable to change date", ex);
        }
    }

    /**
     * Return the current time for the server
     * While the time is not modified send the current date otherwise the fixed date
     *
     * @return The server date
     */
    public static Date getTime() {
        if (calendar == null) {
            return new Date();
        } else {
            return calendar.getTime();
        }

    }

}
