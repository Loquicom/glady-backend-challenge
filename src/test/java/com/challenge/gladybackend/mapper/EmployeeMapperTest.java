package com.challenge.gladybackend.mapper;

import com.challenge.gladybackend.data.dto.EmployeeDTO;
import com.challenge.gladybackend.data.entity.Employee;
import com.challenge.gladybackend.data.mapper.EmployeeMapper;
import com.challenge.gladybackend.helper.EmployeeMaker;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class EmployeeMapperTest {

    @Test
    public void toDTOTest() {
        Employee employee = EmployeeMaker.makeEmployee();
        EmployeeDTO expected = EmployeeMaker.makeEmployeeDTO();

        EmployeeDTO result = EmployeeMapper.toDTO(employee);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void toEntityTest() {
        EmployeeDTO employeeDTO = EmployeeMaker.makeEmployeeDTO();
        Employee expected = EmployeeMaker.makeEmployee();
        expected.setCompany(null);

        Employee result = EmployeeMapper.toEntity(employeeDTO);

        assertThat(result).isEqualTo(expected);
    }

}
