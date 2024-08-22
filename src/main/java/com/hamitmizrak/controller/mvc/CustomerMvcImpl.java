package com.hamitmizrak.controller.mvc;

import com.hamitmizrak.bean.ModelMapperBean;
import com.hamitmizrak.business.dto.CustomerDto;
import com.hamitmizrak.data.entity.CustomerEntity;
import com.hamitmizrak.data.repository.ICustomerRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

// LOMBOK
@RequiredArgsConstructor
@Log4j2

// SPRING MVC
// Dikkat: Spring MVC ile çalışırken;  sistem kararlığı için @GetMapping ile @PostMapping ile çalışın
@Controller
@RequestMapping("/customer/mvc/v1")
public class CustomerMvcImpl implements ICustomerMvc {

    // Variable
    private String modelAttributesTemp = null;

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

    // Model Mapper (DTO=>ENTITY)
    private final ModelMapperBean modelMapperBean;

    ////////////////////////////////////////////////////////////////
    // SPEED DATA
    // http://localhost:4444/customer/mvc/v1/speed
    @Override
    @GetMapping("/speed")
    public String speedData() {
        for (int i = 1; i <= 10; i++) {
            CustomerEntity customerEntity = CustomerEntity.builder()
                    .customerName("adi" + i)
                    .customerSurname("soyadi" + i)
                    .customerEmail("email" + i + "gmail.com")
                    .customerPassword("şifre" + i)
                    .build();
            iCustomerRepository.save(customerEntity);
        }
        modelAttributesTemp = "10 Tane veri eklendi";
        return "redirect:/customer/list";
    }

    // DELETE ALL
    // http://localhost:4444/customer/mvc/v1/deleteAll
    @Override
    @GetMapping("/deleteAll")
    public String deleteAll() {
        iCustomerRepository.deleteAll();
        modelAttributesTemp = "Bütün veriler silindi";
        return "redirect:/customer/list";
    }


    ////////////////////////////////////////////////////////////////////////////////////
    // CREATE GET
    // http://localhost:4444/customer/mvc/v1/create
    @Override
    @GetMapping("/create") // Burası URL
    public String customerCreateGet(Model model) {
        model.addAttribute("key_customer_create", new CustomerDto());
        return "customer_create"; // Burası create sayfasına gidilecek yerdir
    }

    // CREATE POST
    // http://localhost:4444/customer/mvc/v1/create
    @Override
    @PostMapping("/create")
    public String customerCreatePost(
            @Valid @ModelAttribute("customer_create") CustomerDto customerDto,
            BindingResult bindingResult,
            Model model) {
        // Eğer Hata varsa Create Sayfasında kalsın
        if (bindingResult.hasErrors()) {
            //log.error(bindingResult.getAllErrors());
            log.error(bindingResult.hasErrors());
            return "customer_create";
        }
        CustomerEntity customerEntity = modelMapperBean.modelMapperMethod().map(customerDto, CustomerEntity.class);
        iCustomerRepository.save(customerEntity);
        modelAttributesTemp = "Eklendi CustomerDto";
        return "redirect:/customer/list"; // Burası list sayfasına gidilecek yerdir
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
        List<CustomerEntity> customerEntityList = iCustomerRepository.findAll();
        model.addAttribute("key_customer_list", customerEntityList);
        modelAttributesTemp = customerEntityList.size() + " tane veri eklendi";
        model.addAttribute("modelAttributesTemp", modelAttributesTemp);
        return "customer_list"; // Burası list sayfasına gidilecek yerdir
    }

    ////////////////////////////////////////////////////////////////////////////////////
    // FIND
    // http://localhost:4444/customer/mvc/v1/find
    // http://localhost:4444/customer/mvc/v1/find/1
    @Override
    @GetMapping({"/find", "/find/{id}"})
    public String customerFindGet(@PathVariable(name = "id",required = false) Long id, Model model) {
        // Database Veri bulmak
        Optional<CustomerEntity> findCustomerEntity = iCustomerRepository.findById(id);
        // eğer id numaralı veri varsa
        if (findCustomerEntity.isPresent()) {
            model.addAttribute("key_customer_find", findCustomerEntity.get());
            return "customer_view"; // Burası view sayfasına gidilecek yerdir
        }else{ //Yoksa
            model.addAttribute("key_customer_find",id+" nolu Customer Yoktur");
        }
        model.addAttribute("key_customer_find", modelMapperBean.modelMapperMethod().map(findCustomerEntity,CustomerDto.class));
        return "redirect:/customer/list"; // Burası list sayfasına gidilecek yerdir
    }

    ////////////////////////////////////////////////////////////////////////////////////
    // UPDATE GET
    // http://localhost:4444/customer/mvc/v1/update/1
    @Override
    @GetMapping({"/update", "/update/{id}"})
    public String customerUpdateGet(@PathVariable(name = "id",required = false) Long id, Model model) {
        modelAttributesTemp = id + " numaralı veri yoktur";
        CustomerDto customerDto = null;
        model.addAttribute("key_customer_update", customerDto);
        return "customer_update"; // Burası create sayfasına gidilecek yerdir
    }

    // UPDATE POST
    // http://localhost:4444/customer/mvc/v1/update/1
    @Override
    @PostMapping({"/update", "/update/{id}"})
    public String customerUpdatePost(
            @PathVariable(name = "id",required = false) Long id,
            @Valid @ModelAttribute("customer_update") CustomerDto customerDto,
            BindingResult bindingResult,
            Model model) {

        // Eğer Hata varsa Create Sayfasında kalsın
        if (bindingResult.hasErrors()) {
            //log.error(bindingResult.getAllErrors());
            log.error(bindingResult.hasErrors());
            return "customer_update";
        }else{
            // Database Veri bulmak
            Optional<CustomerEntity> findCustomerEntity = iCustomerRepository.findById(id);
            // eğer id numaralı veri varsa
            if (findCustomerEntity.isPresent()) {
                model.addAttribute("key_customer_update", findCustomerEntity.get());
                CustomerEntity customerEntity = modelMapperBean.modelMapperMethod().map(customerDto, CustomerEntity.class);
                iCustomerRepository.save(customerEntity);
                modelAttributesTemp = "Eklendi CustomerDto";
                return "customer_view"; // Burası view sayfasına gidilecek yerdir
            }else{ //Yoksa
                model.addAttribute("key_customer_update",id+" nolu Customer Yoktur");
            }
        }

        // Anasayfaya Göndersin
        return "redirect:/customer/list"; // Burası create sayfasına gidilecek yerdir
    } //end customerCreatePost

    ////////////////////////////////////////////////////////////////////////////////////
    // DELETE
    // http://localhost:4444/customer/mvc/v1/delete/1
    @Override
    @GetMapping({"/delete", "/delete/{id}"})// Not: Thymeleaf için deleteMapping yazmayız
    public String customerDeleteGet(@PathVariable(name = "id",required = false) Long id, Model model) {
        // Database Veri bulmak
        Optional<CustomerEntity> findCustomerDeleteEntity = iCustomerRepository.findById(id);
        // eğer id numaralı veri varsa
        if (findCustomerDeleteEntity.isPresent()) {
            model.addAttribute("key_customer_delete", findCustomerDeleteEntity.get());
            iCustomerRepository.deleteById(id);
            modelAttributesTemp = "Silindi";
        }else{ //Yoksa
            model.addAttribute("key_customer_delete",id+" nolu Customer Yoktur");
        }
        return "redirect:/customer/list"; // Burası list sayfasına gidilecek yerdir
    }

}//end CustomerMvcImpl
