package com.hamitmizrak.controller.mvc;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

// LOMBOK
@RequiredArgsConstructor
@Log4j2

// SPRING MVC
@Controller
@RequestMapping("/customer/mvc/v1")
public class CustomerMvcImpl implements ICustomerMvc{
}
