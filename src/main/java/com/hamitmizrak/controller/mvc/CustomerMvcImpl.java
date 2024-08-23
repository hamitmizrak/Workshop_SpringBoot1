package com.hamitmizrak.controller.mvc;


import com.hamitmizrak.business.dto.CustomerDto;
import com.hamitmizrak.business.services.ICustomerServices;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

// LOMBOK
@RequiredArgsConstructor
@Log4j2
@Getter
@Setter

// SPRING MVC
// Dikkat: Spring MVC ile çalışırken;  sistem kararlığı için @GetMapping ile @PostMapping ile çalışın
@Controller
@RequestMapping("/customer/mvc/v1/")
public class CustomerMvcImpl implements ICustomerMvc {

    // Variable
    private String modelAttributesTemp = "";
    private final ICustomerServices iCustomerServices;


    ////////////////////////////////////////////////////////////////
    // SPEED DATA
    // http://localhost:4444/customer/mvc/v1/speed
    @Override
    @GetMapping("/speed")
    public String speedData() {

        modelAttributesTemp = iCustomerServices.speedDataServices().size()+" Veri eklendi";
        return "redirect:/customer/mvc/v1/list"; // Burası @GetMapping URL gidecek yer.
    }

    // DELETE ALL
    // http://localhost:4444/customer/mvc/v1/deleteAll
    @Override
    @GetMapping("/deleteAll")
    public String deleteAll() {
        iCustomerServices.deleteAllServices();
        modelAttributesTemp = iCustomerServices.customerListServices().size()+" tane Bütün veriler silindi";
        return "redirect:/customer/mvc/v1/list"; // Burası @GetMapping URL gidecek yer.
    }


    ////////////////////////////////////////////////////////////////////////////////////
    // CREATE GET
    // http://localhost:4444/customer/mvc/v1/create
    @Override
    @GetMapping("/create") // Burası URL
    public String customerCreateGet(Model model) {
        model.addAttribute("key_customer_create", new CustomerDto());
        return "customer/customer_create"; // Burası create sayfasına gidilecek yerdir
    }

    // CREATE POST
    // http://localhost:4444/customer/mvc/v1/create
    @Override
    @PostMapping("/create")
    public String customerCreatePost(
            @Valid @ModelAttribute("key_customer_create") CustomerDto customerDto,
            BindingResult bindingResult,
            Model model) {
        // Eğer Hata varsa Create Sayfasında kalsın
        if (bindingResult.hasErrors()) {
            //log.error(bindingResult.getAllErrors());
            log.error(bindingResult.hasErrors());
            return "customer/customer_create";
        }
        iCustomerServices.customerCreateServices(customerDto);
        modelAttributesTemp = "Eklendi CustomerDto"+ customerDto ;
        return "redirect:/customer/mvc/v1/list"; // Burası @GetMapping URL gidecek yer.r.
    }

    ////////////////////////////////////////////////////////////////////////////////////
    // FAKE LIST
    @Override
    public List<CustomerDto> fakeList() {
        List<CustomerDto> customerDtoList = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            CustomerDto customerDto = CustomerDto.builder()
                    .id(Long.valueOf(i))
                    .customerName("name" + i)
                    .customerSurname("surname" + i)
                    .customerPassword(UUID.randomUUID().toString())
                    .customerEmail("email" + i + "@gmail.com")
                    .createdDate(new Date(System.currentTimeMillis()))
                    .build();
            customerDtoList.add(customerDto);
        }
        //return List.of();
        return customerDtoList;
    }

    // LIST
    // http://localhost:4444/customer/mvc/v1/list
    @Override
    @GetMapping("/list")
    public String customerListGet(Model model) {
        // 1.YOL FakeList
        /*
        model.addAttribute("key_customer_list",fakeList());
        modelAttributesTemp=fakeList().size()+" tane veri eklendi";
        */

        // 2.YOL
        model.addAttribute("key_customer_list", iCustomerServices.customerListServices());
        modelAttributesTemp = iCustomerServices.customerListServices().size() + " tane veri eklendi";
        model.addAttribute("modelAttributesTemp", modelAttributesTemp);
        return "customer/customer_list"; // Burası list sayfasına gidilecek yerdir
    }

    ////////////////////////////////////////////////////////////////////////////////////
    // FIND
    // http://localhost:4444/customer/mvc/v1/find
    // http://localhost:4444/customer/mvc/v1/find/1
    @Override
    @GetMapping( "/find/{id}")
    public String customerFindGet(@PathVariable(name = "id") Long id, Model model) {

        // eğer id numaralı veri varsa
        if (iCustomerServices.customerFindServices(id)!=null) {
            model.addAttribute("key_customer_find", iCustomerServices.customerFindServices(id));
            return "customer/customer_view"; // Burası view sayfasına gidilecek yerdir
        }else{ //Yoksa
            model.addAttribute("key_customer_find",id+" nolu Customer Yoktur");
        }
        model.addAttribute("key_customer_find", iCustomerServices.customerFindServices(id));
        return "redirect:/customer/mvc/v1/list"; // Burası @GetMapping URL gidecek yer.
    }

    ////////////////////////////////////////////////////////////////////////////////////
    // UPDATE GET
    // http://localhost:4444/customer/mvc/v1/update/1
    @Override
    @GetMapping( "/update/{id}")
    public String customerUpdateGet(@PathVariable(name = "id") Long id, Model model) {
        modelAttributesTemp = id + " numaralı veri yoktur";
        CustomerDto customerDto = null;
        model.addAttribute("key_customer_update", iCustomerServices.customerFindServices(id));
        return "customer/customer_update"; // Burası create sayfasına gidilecek yerdir
    }

    // UPDATE POST
    // http://localhost:4444/customer/mvc/v1/update/1
    @Override
    @PostMapping( "/update/{id}")
    public String customerUpdatePost(
            @PathVariable(name = "id") Long id,
            @Valid @ModelAttribute("key_customer_update") CustomerDto customerDto,
            BindingResult bindingResult,
            Model model) {

        // Eğer Hata varsa Create Sayfasında kalsın
        if (bindingResult.hasErrors()) {
            //log.error(bindingResult.getAllErrors());
            log.error(bindingResult.hasErrors());
            return "customer/customer_update";
        }else{

            // eğer id numaralı veri varsa
            if (iCustomerServices.customerFindServices(id)!=null) {
                model.addAttribute("key_customer_update", iCustomerServices.customerFindServices(id));
                iCustomerServices.customerUpdateServices(id,customerDto);
                modelAttributesTemp = "Eklendi CustomerDto";
            }else{ //Yoksa
                model.addAttribute("key_customer_update",id+" nolu Customer Yoktur");
                return "customer/customer_update";
            }
        }
        // Anasayfaya Göndersin
        return "redirect:/customer/mvc/v1/list"; // Burası @GetMapping URL gidecek yer.
    } //end customerCreatePost

    ////////////////////////////////////////////////////////////////////////////////////
    // DELETE
    // http://localhost:4444/customer/mvc/v1/delete/1
    @Override
    @GetMapping("/delete/{id}")// Not: Thymeleaf için deleteMapping yazmayız
    public String customerDeleteGet(@PathVariable(name = "id") Long id, Model model) {
        // eğer id numaralı veri varsa
        if (iCustomerServices.customerFindServices(id)!=null) {
            model.addAttribute("key_customer_delete", iCustomerServices.customerFindServices(id));
            iCustomerServices.customerDeleteServices(id);
        }else{ //Yoksa
            model.addAttribute("key_customer_delete",id+" nolu Customer Yoktur");
        }
        return "redirect:/customer/mvc/v1/list"; // Burası @GetMapping URL gidecek yer.
        // iCustomerServices.customerFindServices(id)+" Silindi";
    }

}//end CustomerMvcImpl
