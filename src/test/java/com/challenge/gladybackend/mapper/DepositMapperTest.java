package com.challenge.gladybackend.mapper;

import com.challenge.gladybackend.data.dto.DepositDTO;
import com.challenge.gladybackend.data.entity.Deposit;
import com.challenge.gladybackend.data.mapper.DepositMapper;
import com.challenge.gladybackend.data.request.CreateDepositRequest;
import com.challenge.gladybackend.helper.DepositMaker;
import com.challenge.gladybackend.helper.EmployeeMaker;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Calendar;
import java.util.TimeZone;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class DepositMapperTest {

    @Test
    public void toDTOTest() {
        Deposit deposit = DepositMaker.makeDeposit();
        DepositDTO expected = DepositMaker.makeDepositDTO();

        DepositDTO result = DepositMapper.toDTO(deposit);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void toEntityTest() {
        DepositDTO depositDTO = DepositMaker.makeDepositDTO();
        Deposit expected = DepositMaker.makeDeposit();
        expected.setEmployee(null);

        Deposit result = DepositMapper.toEntity(depositDTO);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void makeTest() {
        CreateDepositRequest request = DepositMaker.makeCreateDepositRequest();
        Deposit expected = DepositMaker.makeDeposit();
        expected.setId(0);

        Deposit result = DepositMapper.make(request, DepositMaker.DEPOSIT_DATE, EmployeeMaker.makeEmployee(), now -> {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(now);
            calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
            calendar.add(Calendar.YEAR, 1);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            return calendar.getTime();
        });

        assertThat(result).isEqualTo(expected);
    }

}
