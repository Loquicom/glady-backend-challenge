package com.challenge.gladybackend.mapper;

import com.challenge.gladybackend.data.mapper.strategy.DepositGiftStrategy;
import com.challenge.gladybackend.data.mapper.strategy.DepositMealStrategy;
import com.challenge.gladybackend.data.mapper.strategy.DepositTypeStrategy;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class DepositTypeStrategyTest {

    public Date getDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    @Test
    public void giftStrategyTest() {
        Date date = getDate(2000, 8, 21);
        Date expected = getDate(2001, 8, 21);

        DepositTypeStrategy strategy = new DepositGiftStrategy();
        Date result = strategy.calculateExpireDate(date);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void mealStrategyTest() {
        Date date = getDate(2000, 8, 21);
        Date expected = getDate(2001, 3, 1);

        DepositTypeStrategy strategy = new DepositMealStrategy();
        Date result = strategy.calculateExpireDate(date);

        assertThat(result).isEqualTo(expected);
    }

}
