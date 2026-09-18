package com.jm.common.RequiredKnowledge;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller  //tab lgate h jab SpringMVC app bnate h , jisme UI(jsp/thymleaf) and backend dono rehte h, api nhi bna rhe , puri application bna rhe
public class A1_SpringWebMVCApp {

    // we have added @Controller , so spring should create obj and manage , let's check , is spring doing this
    public A1_SpringWebMVCApp()
    {
        System.out.println("Spring automatically created object of this clss- SpringWebMVCApp , proff is this constructor is running 😊😊😊");
    }

    // Bind these methods to Http Request Methods using annotations
    //HTTP — sirf ek "rule/format" hai (protocol)
    // ek standard tareeka hai ki client aur server ek dusre se kaise baat karein.
    //client request(URL) ke andar hi method type (GET) aur method name (/healthCheck) likha hota hai
    // agr pom me starter-web nhi dala hota to @RequestMapping me error aati
   @RequestMapping(value="/welcome",method = RequestMethod.GET)
    public String getHealth()
    {

        System.out.println("Fit and fine.. 200 status code 😊 ");
        return "Health check successful";
    }
    // response - There was an unexpected error (type=Internal Server Error, status=500).
    // b/c we are sending data to browser,
    //Error -  Check your ViewResolver setup! (
    // currently spring searching view(html,jsp,thymleaf in resource folder) with name  "Health check successful"
    // if we can create view(html,jsp) then this code is fine.

    //since we have not created view and we are returing response to browser so
    // To fix this error, we can use @ResponseBody annotation on the method to
    // indicate that the return value to browser.


    @RequestMapping(value="/welcomeMsg",method = RequestMethod.GET)
    @ResponseBody   // ye hmara data ko convert krke browser ko uske form me de dega
    public String getHealth1()
    {

        System.out.println("Fit and fine.. 200 status code 😊 ");
        return "Health check successful by Spring Web App Ex";
    }
// http://localhost:8080/welcomeMsg
  //  o/p - Health check successful


    // For data transfer between controller to view - using Model ,
    // model.addAttribute  , in view (jsp/html) - model.getAttribute


    // how data come from view to controller
    //1.@RequestParam - url me jo query string ,
    // usko extract krne ke liye ya phir user ne kuch bheja ho jaise postman me bhejte h -> users?Id=101
    // 2.@ModelAttribute
    // 3. HttpServeletRequest - request.getParamater()
    // 4. PathVariable - url me data h, usko extract krne ke liye -> users/101




    // 2 bache h request mapping ke -
    // @GetMapping
    //@PostMapping



}
