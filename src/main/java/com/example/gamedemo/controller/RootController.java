package com.example.gamedemo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/")
public class RootController {


    @RequestMapping(method = RequestMethod.GET)
    public String swaggerUi() {

        return "redirect:/swagger-ui.html";

    }
}
