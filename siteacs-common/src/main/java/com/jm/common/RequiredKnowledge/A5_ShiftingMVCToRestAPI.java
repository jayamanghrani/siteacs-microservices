package com.jm.common.RequiredKnowledge;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class A5_ShiftingMVCToRestAPI {


}


/*
Asli Problem (jo isse pehle thi)

Spring MVC shuru mein sirf web pages banane ke liye design hua tha (2000s ke early years) — jaise ek method HTML page ka naam return karta tha:

java
@Controller
public class DashboardController {
    @GetMapping("/dashboard")
    public String showDashboard() {
        return "dashboard"; // ye ek STRING hai — Spring isko view/HTML samajhta hai
    }
}

Yahan @Controller ka default assumption hi ye tha: "jo bhi method return karega, wo ek view ka naam hai, HTML dhundo."

Fir jab REST APIs (JSON data dena) popular hone lage — mobile apps, JavaScript frontends (jaise Angular) — logon ko wahi data seedha JSON mein chahiye tha, HTML nahi. Us waqt (Spring 4.0 se pehle) sirf ek hi tarika tha:

java
@Controller
public class BadgeController {
    @GetMapping("/api/badge")
    @ResponseBody   // "nahi bhai, ye view nahi hai, isko JSON bana ke bhej do"
    public Badge getBadge() { ... }
}

Real problem ye thi: Ek hi @Controller class ke andar, kuch methods HTML dete the (return "dashboard"), kuch JSON dete the (@ResponseBody laga hua). Koi bhi dekh ke bata nahi sakta tha class ka actual purpose kya hai — ye ek page-serving controller hai ya ek API controller hai — jab tak har method ko individually padho.

        Isse do dikkatein hoti thi:

Boilerplate — har API method pe baar-baar @ResponseBody likhna.
Semantic confusion (asli wali problem) — code padhne wala (ya khud developer 6 mahine baad) confuse ho jaata tha ki ye class UI ke liye hai ya API ke liye, kyunki dono ek hi annotation (@Controller) use kar rahe the.
Isliye 2013 mein (Spring 4.0) @RestController add kiya gaya

Iska purpose sirf "kam likhna" nahi tha — asli maksad tha: class level pe hi clearly declare kar dena ki "ye poori class REST API ke liye hai, isme koi HTML view nahi aayegi." Jaise hi koi @RestController dekhta hai, bina method padhe hi pata chal jata hai ki ye purely data/API layer hai.

        To short mein: problem thi mixed-purpose controllers ki confusion + repeated @ResponseBody,
        aur @RestController uska solution tha — sirf shortcut ke liye nahi, balki class ka intent clearly
        bata dene ke liye.

        2 line for interview

"@RestController ek shortcut annotation hai jo @Controller aur @ResponseBody ko combine kar deta hai —
 poori class ke methods ko automatically JSON/data return karne ke liye set kar deta hai,
 bina har method pe @ResponseBody baar-baar likhe.

@Controller traditionally view/HTML page return karta hai for server-side rendering (Thymeleaf/JSP),
 jabki @RestController REST APIs ke liye use hota hai jahan sirf data (JSON) chahiye, jaise Angular/mobile clients ke liye."

ab ye server-side rendering ka kya scene h , mujhe ache se btana, ese shortcut me response pasand nhi mujhe

        Theek hai, poora explain karta hoon step by step — bina shortcut ke.

Pehle samjho: Browser mein page kaise dikhta hai

Jab bhi aap koi website kholte ho, browser ko HTML chahiye hota hai dikhane ke liye. Sawaal ye hai — wo HTML banata kaun hai, aur kahan banta hai?

Do tarike hain iske:

        1. Server-Side Rendering (SSR)

Matlab: HTML poora server pe (backend pe) banta hai, aur ready-made HTML page browser ko bhej diya jata hai.

Kaise kaam karta hai, step by step:

Aap browser mein URL daalte ho: localhost:8080/dashboard
Ye request server (Spring Boot app) ke paas jaati hai.
Server ke paas ek template file hoti hai (jaise dashboard.html — Thymeleaf ya JSP format mein),
jisme kuch placeholders hote hain data ke liye:
html
   <h1>Welcome, {{contractorName}}</h1>
<p>Your badge status: {{status}}</p>
Server database se data nikalta hai (contractor ka naam, status),
aur us template ke placeholders mein wahi pe fill kar deta hai:
java
@Controller
public class DashboardController {
    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        model.addAttribute("contractorName", "Rahul");
        model.addAttribute("status", "Pending");
        return "dashboard"; // Spring is naam ki template dhundega
    }
}
Server ek poora, final HTML page bana ke bhej deta hai:
html
   <h1>Welcome, Rahul</h1>
<p>Your badge status: Pending</p>
Browser ko sirf ye ready HTML dikhana hai — usko koi extra kaam nahi karna, seedha render kar deta hai.

Yehi hai server-side rendering — matlab HTML "render" (banana/taiyar karna) server ki side pe ho raha hai,
browser sirf dikhata hai.

        2. Client-Side Rendering (CSR) — jo Angular karta hai

Matlab: Server sirf JSON data deta hai, HTML browser (client) khud apni JavaScript se banata hai.

Kaise kaam karta hai, step by step:

Browser pehle sirf ek khaali si HTML file load karta hai (Angular ki index.html) —
usme koi real content nahi hota, bas ek <div id="app"></div>.
Us khaali HTML ke saath JavaScript files (Angular ka poora code) bhi load hoti hain.
Angular ki JavaScript khud backend ko REST API call karti hai:
GET /api/contractor/dashboard
Backend (@RestController use karke) sirf JSON data deta hai, koi HTML nahi:
json
{ "contractorName": "Rahul", "status": "Pending" }
Ab Angular ki JavaScript browser ke andar hi, us JSON data ko lekar, khud HTML bana ke us khaali <div> ke andar daal deti hai:
html
   <h1>Welcome, Rahul</h1>
<p>Your badge status: Pending</p>

Yehi hai client-side rendering — HTML banane ka kaam server nahi, browser (client) khud kar raha hai, apni JavaScript se.

Dono ka fark side by side
                            Server-Side Rendering	              Client-Side Rendering (Angular)
HTML kaun banata hai	Backend server (Spring Boot + Thymeleaf/JSP)	Browser (Angular ki JavaScript)
Backend kya bhejta hai	Poora ready HTML page	                       Sirf JSON data
Controller annotation	@Controller (return string = view name)  	@RestController (return object = JSON)
Backend aur UI	         Ek hi app, tightly coupled             	Alag-alag apps
Pehli baar load hone mein	Thoda fast dikh sakta hai (HTML seedha ready aata hai)	Thoda slow ho sakta hai (pehle JS load, fir data fetch, fir render)
Mobile app bhi wahi backend use kar sake?	Mushkil — backend HTML deta hai, mobile app ko HTML nahi
JSON chahiye	Aasan — backend already JSON deta hai, mobile app bhi wahi API use kar sakta hai
Aapke project ke context mein

Aapka SiteAcsBackend JSON dega (@RestController) → ye client-side rendering wala pattern hai,
 kyunki Angular (client) khud HTML banayega browser mein, data sirf backend se aayega.

Agar aap Spring MVC + Thymeleaf use karte (@Controller, return "dashboard"),
to wo server-side rendering hota — HTML server pe hi ban ke aata,
Angular ki zaroorat hi nahi padti UI ke liye.

Isliye aapke case mein jo humne pehle discuss kiya tha (separate backend + Angular) —
 us mein backend hamesha @RestController hi use karega, kyunki Angular khud client-side pe HTML render karega,
  server sirf data (JSON) degi.
*/
