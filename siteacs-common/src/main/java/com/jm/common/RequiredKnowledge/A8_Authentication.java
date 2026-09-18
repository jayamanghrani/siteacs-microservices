package com.jm.common.RequiredKnowledge;

public class A8_Authentication {
}


/*
jaise hi security dependency add hoti  h , spring extra care krta h
//
kehta h , mai kisi ko bhi backend me kuch update nhi krne dunga
// ya to token bhejo , ya user name pwd bhejo
// to jab hmm springMVC app bnate h to CRMS token bhejte h
// when Rest API- JWT
// login based simple demo app - username , pwd


// how JWT work

ese bnate h token
HAshing eski[encoded header + encoded data + (secret key+encoded header+ encoded data)]

token return hoke client ke pass jata h , angular ya react pe
vaha localstorge me JWT store hoga, jo hacker ko hack krne me tuf hota h
csrf cookie me store hota h , jo hack krna esay h

token eliminiate the need of session state




// CSRF ke baare me
csrf kab - session se related gadbad ho rhi h - by hacker
hacker hack kr rha ho

when we create form based applications - session id bnti h ,
server bhejta h session id - jo ki cookie me store hoti h
hackend faltu link pe click krvake, hmse backend call krvata h
esse bchne ke liye csrf token aaya
jaha session vaha csrf token ,
rest api - no session - no csrf
csrf ke time
token bhejte h har api ko , and other user related data session me
rehta backend me uthate use,
konse user ne kiya h login , first time aaya ki pehle aa chuka h , uska name, expiry session ka time ......
JWT token --- token + data(user related data), session ka koi role nhi h yaha
security me data ko claims bolte h

har api data token se hi legi , session ka koi role nhi
that's why JWT make  api   stateless */
