package com.challenge.gladybackend.repository;

import com.challenge.gladybackend.data.entity.Deposit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class DepositRepositoryTest {

    public static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private DepositRepository repository;

    @Test
    public void contextLoad() {
        assertThat(repository).isNotNull();
    }

    @Test
    public void findByTypeTest() {
        List<Deposit> deposits = repository.findByType("GIFT");
        int sum = deposits.stream().mapToInt(Deposit::getAmount).sum();
        assertThat(deposits.size()).isEqualTo(2);
        assertThat(sum).isEqualTo(180);

        deposits = repository.findByType("MEAL");
        sum = deposits.stream().mapToInt(Deposit::getAmount).sum();
        assertThat(deposits.size()).isEqualTo(2);
        assertThat(sum).isEqualTo(100);
    }

    @Test
    public void findByExpireAfterTest() throws ParseException {
        Date d = formatter.parse("2022-08-20");
        List<Deposit> deposits = repository.findByExpireAfter(d);
        int sum = deposits.stream().mapToInt(Deposit::getAmount).sum();
        assertThat(deposits.size()).isEqualTo(2);
        assertThat(sum).isEqualTo(160);

        d = formatter.parse("2020-08-20");
        deposits = repository.findByExpireAfter(d);
        sum = deposits.stream().mapToInt(Deposit::getAmount).sum();
        assertThat(deposits.size()).isEqualTo(4);
        assertThat(sum).isEqualTo(280);

        d = formatter.parse("2022-02-01");
        deposits = repository.findByExpireAfter(d);
        sum = deposits.stream().mapToInt(Deposit::getAmount).sum();
        assertThat(deposits.size()).isEqualTo(4);
        assertThat(sum).isEqualTo(280);

        d = formatter.parse("2022-02-02");
        deposits = repository.findByExpireAfter(d);
        sum = deposits.stream().mapToInt(Deposit::getAmount).sum();
        assertThat(deposits.size()).isEqualTo(3);
        assertThat(sum).isEqualTo(200);
    }

    @Test
    public void findByTypeAndExpireAfter() throws ParseException {
        Date d = formatter.parse("2022-08-20");
        List<Deposit> deposits = repository.findByTypeAndExpireAfter("GIFT", d);
        int sum = deposits.stream().mapToInt(Deposit::getAmount).sum();
        assertThat(deposits.size()).isEqualTo(1);
        assertThat(sum).isEqualTo(100);

        deposits = repository.findByTypeAndExpireAfter("MEAL", d);
        sum = deposits.stream().mapToInt(Deposit::getAmount).sum();
        assertThat(deposits.size()).isEqualTo(1);
        assertThat(sum).isEqualTo(60);

        d = formatter.parse("2022-02-02");
        deposits = repository.findByTypeAndExpireAfter("GIFT", d);
        sum = deposits.stream().mapToInt(Deposit::getAmount).sum();
        assertThat(deposits.size()).isEqualTo(1);
        assertThat(sum).isEqualTo(100);

        deposits = repository.findByTypeAndExpireAfter("MEAL", d);
        sum = deposits.stream().mapToInt(Deposit::getAmount).sum();
        assertThat(deposits.size()).isEqualTo(2);
        assertThat(sum).isEqualTo(100);
    }

}
