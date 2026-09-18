package com.jm.common.RequiredKnowledge;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


//Agar status control karna hai
//        (success/fail/not-found/unauthorized alag-alag
//                cases mein) → ResponseEntity zaroori hai.
//Real projects mein har API mein ResponseEntity use karna best practice mana jata hai

@RestController
public class A6_ResponseEntity {

    @GetMapping("/api/badge/{id}")
    public Employee getBadge(@PathVariable Long id) {
        Employee emp = new Employee();
        //emp = emp.findById();
        return emp;
    }
}

//Ye kaam kar jaata hai — JSON return ho jata hai. Lekin ek badi problem hai:
//
//Spring hamesha HTTP status 200 OK bhejega — chahe kuch bhi ho jaaye.
//
//Ab socho:
//
//Agar id galat hai aur badge mila hi nahi (badge = null) → phir bhi response 200 OK jayega, sirf body mein null hoga.
//Agar validation fail ho gayi (jaise photo missing hai) → phir bhi 200 OK jayega.
//Agar server mein koi galti ho gayi → phir bhi (agar handle nahi kiya) 200 OK ya generic 500 jayega, but aap control nahi kar sakte kaunsa status kab jaana chahiye.
//
//Ye real problem hai — kyunki jo bhi is API ko call kar raha hai (Angular, mobile app, Postman), unhe status code se hi pata chalta hai ki request successful thi ya nahi. Sirf JSON body dekh ke pata lagana padega — jo galat design hai.
//
//

/*
Solution: ResponseEntity

ResponseEntity ek wrapper class hai jo aapko 3 cheezein control karne deti hai ek saath:

1.Status code (200, 404, 400, etc.)
2.Headers (agar custom headers bhejni ho)
3.Body (actual data — JSON)*/
