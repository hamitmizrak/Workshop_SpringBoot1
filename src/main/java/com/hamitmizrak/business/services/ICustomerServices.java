package com.hamitmizrak.business.services;

import com.hamitmizrak.business.dto.CustomerDto;
import com.hamitmizrak.data.entity.CustomerEntity;

import java.util.List;

public interface ICustomerServices {

    // MODEL MAPPER Customer
    public CustomerDto entityToDto(CustomerEntity customerEntity);
    public CustomerEntity dtoToEntity(CustomerDto customerDto);

    // SPEED DATA Customer
    public List<CustomerDto> speedDataServices();

    // DELETE ALL Customer
    public void deleteAllServices();

    // CREATE POST Customer
    CustomerDto customerCreateServices(CustomerDto customerDto);

    // LIST Customer
    List<CustomerDto> customerListServices();

    // FIND Customer
    CustomerDto customerFindServices(Long id);

    // UPDATE POST
    CustomerDto customerUpdateServices(Long id, CustomerDto customerDto);

    // DELETE Customer
    CustomerDto customerDeleteServices(Long id);

} // end ICustomerServices
