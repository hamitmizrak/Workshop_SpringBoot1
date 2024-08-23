package com.hamitmizrak.business.services.impl;

import com.hamitmizrak.bean.ModelMapperBean;
import com.hamitmizrak.bean.PasswordEncoderBean;
import com.hamitmizrak.business.dto.CustomerDto;
import com.hamitmizrak.business.services.ICustomerServices;
import com.hamitmizrak.data.entity.CustomerEntity;
import com.hamitmizrak.data.repository.ICustomerRepository;
import com.hamitmizrak.exception.NotFound44Exception;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;


// LOMBOK
@RequiredArgsConstructor

// Services
@Service
public class CustomerServiceImpl implements ICustomerServices {

    //Injection
    private final ModelMapperBean modelMapperBean;
    private final PasswordEncoderBean passwordEncoderBean;

    // Injection
    // 1.YOL (ICustomerRepository)
    /*
    @Autowired
    private ICustomerRepository iCustomerRepository;
    */

    //2.YOL (ICustomerRepository)
    /*
    private final ICustomerRepository iCustomerRepository;
    @Autowired // Eğer 1 tane variable varsa @Autowired yazmak zorunda değilsiniz.
    public CustomerMvcImpl(ICustomerRepository iCustomerRepository) {
        this.iCustomerRepository = iCustomerRepository;
    }
    */

    //3.YOL (ICustomerRepository)
    private final ICustomerRepository iCustomerRepository;


    // MODEL MAPPER
    @Override
    public CustomerDto entityToDto(CustomerEntity customerEntity) {
        return modelMapperBean.modelMapperMethod().map(customerEntity, CustomerDto.class);
    }

    @Override
    public CustomerEntity dtoToEntity(CustomerDto customerDto) {
        return modelMapperBean.modelMapperMethod().map(customerDto, CustomerEntity.class);
    }

    ////////////////////////////////////////////////////////////////
    // SPEED DATA
    // http://localhost:4444/customer/mvc/v1/speed
    @Override
    public List<CustomerDto> speedDataServices() {
        List<CustomerDto> customerDtoList = new ArrayList<>();
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
            customerDto = entityToDto(customerEntity);
            customerDtoList.add(customerDto);
            System.out.println(customerDto);
        }
        return customerDtoList;
    }

    // DELETE ALL
    // http://localhost:4444/customer/mvc/v1/deleteAll
    @Override
    public void deleteAllServices() {
        iCustomerRepository.deleteAll();
    }


    ////////////////////////////////////////////////////////////////////////////////////
    // CREATE
    // http://localhost:4444/customer/mvc/v1/create
    @Override
    public CustomerDto customerCreateServices(CustomerDto customerDto) {
        // Password Masking
        System.out.println(customerDto.getCustomerPassword());
        customerDto.setCustomerPassword(passwordEncoderBean.passwordEncoderMethod().encode(customerDto.getCustomerPassword()));

        CustomerEntity customerEntityCreate = dtoToEntity(customerDto);
        iCustomerRepository.save(customerEntityCreate);
        // Id Set
        customerDto.setId(customerEntityCreate.getId());
        return customerDto;
    }

    ////////////////////////////////////////////////////////////////////////////////////
    // LIST
    // http://localhost:4444/customer/mvc/v1/list
    @Override
    public List<CustomerDto> customerListServices() {
        List<CustomerDto> customerDtoList = new ArrayList<>();
        for (CustomerEntity entity : iCustomerRepository.findAll()) {
            CustomerDto customerDto = entityToDto(entity);
            customerDtoList.add(customerDto);
        }
        // return List.of();
        return customerDtoList;
    }

    ////////////////////////////////////////////////////////////////////////////////////
    // FIND
    // http://localhost:4444/customer/mvc/v1/find
    @Override
    public CustomerDto customerFindServices(Long id) {
        // 1.YOL
        CustomerEntity customerEntityFind = iCustomerRepository.findById(id).orElseThrow(
                () -> new NotFound44Exception(id + " numaralı id bulunamadı")
        );

        // 2.YOL
        /*
        // Database Veri bulmak
        Optional<CustomerEntity> findCustomerEntity = iCustomerRepository.findById(id);
        // eğer id numaralı veri varsa
        if (findCustomerEntity.isPresent()) {
            CustomerDto customerDto = entityToDto(findCustomerEntity.get());
        }
        */

        CustomerDto customerDto = entityToDto(customerEntityFind);
        return customerDto;
    }

    ////////////////////////////////////////////////////////////////////////////////////
    // UPDATE
    // http://localhost:4444/customer/mvc/v1/update/1
    @Override
    public CustomerDto customerUpdateServices(Long id, CustomerDto customerDto) {
        // Password Masking
        customerDto.setCustomerPassword(passwordEncoderBean.passwordEncoderMethod().encode(customerDto.getCustomerPassword()));

        // Find
        CustomerDto customerDtoFind= customerFindServices(id);
        CustomerEntity customerEntity=dtoToEntity(customerDtoFind);

        // Update
        customerEntity.setCustomerName(customerDto.getCustomerName());
        customerEntity.setCustomerSurname(customerDto.getCustomerSurname());
        customerEntity.setCustomerEmail(customerDto.getCustomerEmail());
        customerEntity.setCustomerPassword(customerDto.getCustomerPassword());
        iCustomerRepository.save(customerEntity);

        // Id Set
        customerDto.setId(customerEntity.getId());
        return customerDto;
    }

    ////////////////////////////////////////////////////////////////////////////////////
    // DELETE
    // http://localhost:4444/customer/mvc/v1/delete/1
    @Override
    public CustomerDto customerDeleteServices(Long id) {
        CustomerDto customerDtoFind= customerFindServices(id);
        CustomerEntity customerEntity=dtoToEntity(customerDtoFind);
        // CustomerEntity nesnesi üzerinden silmek
        iCustomerRepository.delete(customerEntity);
        return customerDtoFind;
    } //end delete

} //end CustomerServiceImpl
