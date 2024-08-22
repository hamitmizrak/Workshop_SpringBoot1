package com.hamitmizrak.controller.mvc;

import com.hamitmizrak.business.dto.CustomerDto;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface ICustomerMvc {

    // SPEED DATA
    public String speedData();

    // DELETE ALL
    public String deleteAll();

    // FAKE LIST
    public List<CustomerDto> fakeList();

    //////////////////////////////////////////////////////

    // CREATE GET
    // Model: import org.springframework.ui.Model;
    public String customerCreateGet(Model model);

    // CREATE POST
    public String customerCreatePost(CustomerDto customerDto, BindingResult bindingResult, Model model);

    // LIST
    public String customerListGet(Model model);

    // FIND
    public String customerFindGet(Long id, Model model);

    // UPDATE GET
    public String customerUpdateGet(Long id, Model model);

    // UPDATE POST
    public String customerUpdatePost(Long id,CustomerDto customerDto, BindingResult bindingResult, Model model);

    // DELETE
    public String customerDeleteGet(Long id, Model model);
}
