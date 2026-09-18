package com.jm.common.RequiredKnowledge;

import org.springframework.web.bind.annotation.*;

@RestController
public class A2_SpringAppRestAPI {

    public A2_SpringAppRestAPI() {
        System.out.println("Spring automatically created object of this clss- SpringAppRestAPI , proff is this constructor is running 😊😊😊");
    }

// No need of @RequestBody annotation , data ko automatically convert java obj into json using jakson library , which is present in external libraries , you can check
// @RequestMapping bhi use kr sakte h , but @getMapping jada easy h
    @GetMapping("/sayHello")
    public String sayHelloRestAPI()
    {
        return "Hello from Rest API";
    }

}

// API - jansewa h , allow to communicate between 2 applications, api's public , private bhi hoti h
// using api kisi resource ka data ek jagah se dusri jagah send kr rhe h ,
// [ data = resource ]
//

// Rest API kya hoti h
//6 rule h h , agr vo h to aapki api restful ho gyi h
// representation-dikhana
// state - ek resource ka data, ki state
// transfer -  jo data transfer ho rha h,

// Rest API ka birth time
// RPC - remote procedure call  , RPC convert xml RPC, SOAP(with restriction) aaya phir ,
// REST(free h , guidline na mano to bhi chlega) aaya phir , GraphQL aaya h abhi

// Http protocol command
// get - read
//POST - create
// put - update, multiple changes
// patch - ek part ko update
// delete - delete


// API url kaise likhte h, en rules ko na mane to bhi restful api hogi, bindas h
// 1. Resource mention - user ya product ki detail chaheye to vo likho url me user product
// 2. response - json, xml, plaintext ho sakta h
//3. Http Methos hone chaheye
//4. response kahi bhi save nhi hota in main server  it is stateless,
// krna h to server end pe cache implement kr sakte h


// status code
// 1xx -  server processing
// 2xx- success
// 3xx- redirection issue
// 4xx - client side se issue
// 500 - server pe error aa gya h


// 401- unauthorized
// 403 - No permission
// 404 - server pe esa nhi likha , jaisa tumne likh h ya jab no record found vala scenario ho
// 405 - server pe method post h tumne get kiya h , ya viceversa

// 503- server  down h

// Spring MVC App me 3 dependency add krte the -
// jasper - view ke liye

//@Controller - tab lgate h jab SpringMVC app bnate h ,
// jisme UI and backend dono rehte h, janseva nhi h

// @RestController -  jab sirf backend bna rhe h , api bna rhe h ,
// aor client ko raw data denge,
// public rkha to multiple client ho sakte , private rkha to sirf vahi jinke pass  password hoga , jinko hmm pwd denge

// Springboot me jsper nhi chaheye ,
// yaha jakson chaheye jo springbbot automatically add krega

