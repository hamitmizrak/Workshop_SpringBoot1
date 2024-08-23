package com.hamitmizrak.dataset;

import com.hamitmizrak.bean.PasswordEncoderBean;
import com.hamitmizrak.business.dto.CustomerDto;
import com.hamitmizrak.data.entity.CustomerEntity;
import com.hamitmizrak.data.repository.ICustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

// LOMBOK
@RequiredArgsConstructor
@Log4j2

// Bean
@Configuration
public class DataSetMainRunner {

    // Injection
    private final ICustomerRepository iCustomerRepository;
    private final PasswordEncoderBean passwordEncoderBean;

    @Bean
    public CommandLineRunner dataSet() {
        return args -> {
            log.info("Dataset");
            CustomerDto customerDto;
            for (int i = 1; i <= 10; i++) {
                customerDto = new CustomerDto();
                CustomerEntity customerEntity = CustomerEntity.builder()
                        .customerName("adi" + i)
                        .customerSurname("soyadi" + i)
                        .customerEmail("email" + i + "gmail.com")
                        //.customerPassword(passwordEncoderBean.passwordEncoderMethod().encode(UUID.randomUUID().toString()))
                        .customerPassword(passwordEncoderBean.passwordEncoderMethod().encode("root"))
                        .build();
                iCustomerRepository.save(customerEntity);

                log.info(customerDto);
                System.out.println(customerDto);
            }
        };

    }



}
