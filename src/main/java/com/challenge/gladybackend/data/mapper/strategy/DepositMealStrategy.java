package com.challenge.gladybackend.data.mapper.strategy;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DepositMealStrategy implements DepositTypeStrategy {

    @Override
    public Date calculateExpireDate(Date now) {
        // Get calendar
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        // Add one year
        calendar.add(Calendar.YEAR, 1);
        // Set to 1st march (first day expired)
        calendar.set(Calendar.MONTH, 2);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        // Set other variable to 0
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        // Return date
        return calendar.getTime();
    }

}
