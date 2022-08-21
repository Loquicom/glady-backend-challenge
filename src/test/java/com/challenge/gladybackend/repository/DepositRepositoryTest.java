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
    public void findByEmployeeTest() {
        List<Deposit> deposits = repository.findByEmployee_Id(0);
        double sum = deposits.stream().mapToDouble(Deposit::getAmount).sum();
        assertThat(deposits.size()).isEqualTo(4);
        assertThat(sum).isEqualTo(280);
    }

    @Test
    public void findByTypeTest() {
        List<Deposit> deposits = repository.findByTypeAndEmployee_Id("GIFT", 0);
        int sum = deposits.stream().mapToInt(Deposit::getAmount).sum();
        assertThat(deposits.size()).isEqualTo(2);
        assertThat(sum).isEqualTo(180);

        deposits = repository.findByTypeAndEmployee_Id("MEAL", 0);
        sum = deposits.stream().mapToInt(Deposit::getAmount).sum();
        assertThat(deposits.size()).isEqualTo(2);
        assertThat(sum).isEqualTo(100);
    }

    @Test
    public void findByExpireAfterTest() throws ParseException {
        Date d = formatter.parse("2022-08-20");
        List<Deposit> deposits = repository.findByExpireAfterAndEmployee_Id(d, 0);
        int sum = deposits.stream().mapToInt(Deposit::getAmount).sum();
        assertThat(deposits.size()).isEqualTo(2);
        assertThat(sum).isEqualTo(160);

        d = formatter.parse("2020-08-20");
        deposits = repository.findByExpireAfterAndEmployee_Id(d, 0);
        sum = deposits.stream().mapToInt(Deposit::getAmount).sum();
        assertThat(deposits.size()).isEqualTo(4);
        assertThat(sum).isEqualTo(280);

        d = formatter.parse("2022-02-01");
        deposits = repository.findByExpireAfterAndEmployee_Id(d, 0);
        sum = deposits.stream().mapToInt(Deposit::getAmount).sum();
        assertThat(deposits.size()).isEqualTo(4);
        assertThat(sum).isEqualTo(280);

        d = formatter.parse("2022-02-02");
        deposits = repository.findByExpireAfterAndEmployee_Id(d, 0);
        sum = deposits.stream().mapToInt(Deposit::getAmount).sum();
        assertThat(deposits.size()).isEqualTo(3);
        assertThat(sum).isEqualTo(200);
    }

    @Test
    public void findByTypeAndExpireAfter() throws ParseException {
        Date d = formatter.parse("2022-08-20");
        List<Deposit> deposits = repository.findByTypeAndExpireAfterAndEmployee_Id("GIFT", d, 0);
        int sum = deposits.stream().mapToInt(Deposit::getAmount).sum();
        assertThat(deposits.size()).isEqualTo(1);
        assertThat(sum).isEqualTo(100);

        deposits = repository.findByTypeAndExpireAfterAndEmployee_Id("MEAL", d, 0);
        sum = deposits.stream().mapToInt(Deposit::getAmount).sum();
        assertThat(deposits.size()).isEqualTo(1);
        assertThat(sum).isEqualTo(60);

        d = formatter.parse("2022-02-02");
        deposits = repository.findByTypeAndExpireAfterAndEmployee_Id("GIFT", d, 0);
        sum = deposits.stream().mapToInt(Deposit::getAmount).sum();
        assertThat(deposits.size()).isEqualTo(1);
        assertThat(sum).isEqualTo(100);

        deposits = repository.findByTypeAndExpireAfterAndEmployee_Id("MEAL", d, 0);
        sum = deposits.stream().mapToInt(Deposit::getAmount).sum();
        assertThat(deposits.size()).isEqualTo(2);
        assertThat(sum).isEqualTo(100);
    }

}
