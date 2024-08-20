package com.hamitmizrak.controller.mvc;

import com.hamitmizrak.business.dto.CustomerDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

// LOMBOK
@RequiredArgsConstructor
@Log4j2

// SPRING MVC
@Controller
@RequestMapping("/customer/mvc/v1")
public class CustomerMvcImpl implements ICustomerMvc{

    // Variable
    private String modelAttributesTemp =null;

    // Injection


    ////////////////////////////////////////////////////////////////
    // SPEED DATA
    // http://localhost:4444/customer/mvc/v1/speed
    @Override
    @GetMapping("/speed")
    public String speedData() {
        modelAttributesTemp ="5 Tane veri eklendi";
        return "redirect:/customer/list";
    }

    //DELETE ALL
    // http://localhost:4444/customer/mvc/v1/deleteall
    @Override
    @GetMapping("/deleteall")
    public String deleteAll() {
        modelAttributesTemp ="Bütün veriler silindi";
        return "redirect:/customer/list";
    }

    // FAKE LIST
    @Override
    public List<CustomerDto> fakeList() {
        List<CustomerDto> customerDtoList = new ArrayList<>();
        for (int i = 1; i <=9 ; i++) {
            CustomerDto customerDto=CustomerDto.builder()
                    .id(Long.valueOf(i))
                    .name("name"+i)
                    .surname("surname"+i)
                    .password(UUID.randomUUID().toString())
                    .name("email"+i+"@gmail.com")
                    .createdDate(new Date(System.currentTimeMillis()))
                    .build();
            customerDtoList.add(customerDto);
        }
        //return List.of();
        return customerDtoList;
    }

    ////////////////////////////////////////////////////////////////
    // CREATE GET
    // http://localhost:4444/customer/mvc/v1/create
    @Override
    @GetMapping("/create")
    public String customerCreateGet(Model model) {
        model.addAttribute("customer_create",new CustomerDto());
        return "customer/create";
    }

    // CREATE POST
    // http://localhost:4444/customer/mvc/v1/create
    @Override
    @GetMapping("/create")
    public String customerCreatePost(
            @Valid @ModelAttribute("customer_create") CustomerDto customerDto,
            BindingResult bindingResult,
            Model model) {
        modelAttributesTemp="Eklendi CustomerDto";
        return "redirect:/customer/list";
    }

    // LIST
    // http://localhost:4444/customer/mvc/v1/list
    @Override
    @GetMapping("/list")
    public String customerListGet(Model model) {
        model.addAttribute("customer_list",fakeList());
        modelAttributesTemp=fakeList().size()+" tane veri eklendi";
        model.addAttribute("modelAttributesTemp",modelAttributesTemp);
        return "customer/list";
    }

    // FIND
    // http://localhost:4444/customer/mvc/v1/find/1
    @Override
    @GetMapping("/find/{id}")
    public String customerFindGet(@PathVariable(name="id") Long id, Model model) {
        return "customer/view";
    }

    // DELETE
    // http://localhost:4444/customer/mvc/v1/delete/1
    @Override
    @GetMapping("/find/{id}")// Not: Thymeleaf için deleteMapping yazmayız
    public String customerDeleteGet(@PathVariable(name="id") Long id, Model model) {
        modelAttributesTemp="Silindi";
        return "redirect:/customer/list";
    }

    // UPDATE GET
    // http://localhost:4444/customer/mvc/v1/update/1
    @Override
    @GetMapping("/update/{id}")
    public String customerUpdateGet(@PathVariable(name="id") Long id, Model model) {
        modelAttributesTemp=id+" numaralı veri yoktur";
        CustomerDto customerDto=null;
        model.addAttribute("customer_update",customerDto);
        return "customer/update";
    }

    // UPDATE POST
    // http://localhost:4444/customer/mvc/v1/update/1
    @Override
    @GetMapping("/update/{id}")
    public String customerCreatePost(
            @PathVariable(name="id") Long id,
            @Valid @ModelAttribute("customer_update") CustomerDto customerDto,
            BindingResult bindingResult,
            Model model) {
        modelAttributesTemp=id+" güncellendi"+customerDto;
        return "redirect:/customer/list";
    } //end customerCreatePost

}//end CustomerMvcImpl
