
package com.jm.backend.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class RateLimitInterceptor implements HandlerInterceptor {

    private static final int MAX_REQUESTS_PER_WINDOW = 3;
    private static final long WINDOW_MILLIS = 60_000; // 1 minute

  // ek hi map bnega puri application , chaheye kitne bhi user hit kre , request bheje ,
  // ek hi map me sab store hoga
    // taki correct count pta chle chahe same ip se aaya ho ya alag ip se request
    private final ConcurrentHashMap<String, RequestWindow> requestCounts = new ConcurrentHashMap<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        String email = request.getParameter("email");
        if (email == null) {
            email = request.getHeader("X-User-Email"); // fallback, agar body se lena ho to alag tarika chahiye
        }

        // IP nikala jisne request bheji h
        String clientKey = request.getRemoteAddr();

        //agar ye koi nya ip aaya h to nya request window object bnao,
        // agar pehle bhi ip aa chuka h to vahi obj return kr do
        RequestWindow window = requestCounts.computeIfAbsent(clientKey, k -> new RequestWindow());

        //Agar HAR-THREAD, GENUINELY, HAMESHA, ALAG-ALAG-IP se ho, to synchronized ki jarurat nahi h,
        // but agar same-IP se multiple request/thread ek hi time pe aa rahe h to synchronized ki jarurat h
        //synchronized — ye batаता hai "is block ke andar, EK-TIME-PE-SIRF-EK-THREAD hi kaм kare"
        // kyoki jab same ip(network) se bohot sare hit aaye to jitne hit aayenge utne thread bnenge ,
        // ab same ip ke case me , vo multiple thread ek hi ip vale record ko update krenge to data override ho sakta h kyoki multiple thread h
       // so synchronized means ek ek krke update kro bhai
        synchronized (window) {
            long now = System.currentTimeMillis();

           // Agar 1-minute (60,000 ms) se ZYADA time bit gaya → matlab purана-window "expire" ho gaya → naya window shuru karo
            if (now - window.windowStart > WINDOW_MILLIS) {
                // Naya window shuru karo
                window.windowStart = now;
                window.count.set(0);
            }

            if (window.count.incrementAndGet() > MAX_REQUESTS_PER_WINDOW) {
                response.setStatus(429); // Too Many Requests
                response.setContentType("application/json");
                //kyunki ye Controller nahi hai, isлिए ResponseEntity use nahi kar sakte — Interceptor mein, response-object ko directly manipulate karna padता hai).
                response.getWriter().write("{\"success\":false,\"message\":\"Too many OTP requests. Please try again later.\"}");
                return false; // request yahin rok do, controller tak nahi jaane do
            }
        }

        return true; // aage badhne do
    }

    private static class RequestWindow {
        long windowStart = System.currentTimeMillis();
        AtomicInteger count = new AtomicInteger(0);
    }
}


// Tomcat (Jo Spring Boot Ke Andar "Embedded" Hai) — Apна Khud Ka Thread-Pool Rakhtа Hai
//properties
//server.tomcat.threads.max=200        # Default, max 200 threads
//server.tomcat.threads.min-spare=10   # Minimum 10, hamesha ready
//
//Matlab — Tomcat, "unlimited" threads NAHI banаता — usके paas ek FIXED-SIZE POOL hai (default 200). Jab-bhи request aती hai, Tomcat, PODLE-se-EXISTING pool mein se, ek "free" thread ko PICK karта hai, use kaм deта hai.
//
//200 requests EK-SAATH aईं → sabko, POOL ke 200-threads mil jaenge, sab PARALLEL process hongi
//
//201st request aई (jab sab 200-threads busy hain) →
//   Ye request, "QUEUE" mein WAIT karегi, jab tak koई thread "free" na ho jaए
//
//Isлिए — "infinite threads" ka koई risk nahi hai — server, khud, "controlled" tarikе se, threads-ko-manage karta hai, aur agar limit-cross ho jaए, NAYE-REQUESTS ko WAIT karwa deta hai (crash nahi hote).


/*

Layer	Thread-Pool	Size
Tomcat (incoming HTTP-requests)	Web-server ka apna pool	Default 200
@Async (Email bhejने ke liए)	Aapка custom ThreadPoolTaskExecutor	Aapने max=10 set kiya thа
Database-Connection-Pool (HikariCP)	DB-connections ka apna pool	Default ~10*/


/*"Bahut-log-EK-SAATH" — ye do ALAG-scale ki cheezein ho sakti hain:

Scale	                                                 Kaisе Handle Hota Hai
Ek-server, 200-threads tak (jo humне discuss kiya)	    Single-machine, thread-pool se handle hota hai
HAZAROON/LAKHOON users, EK-SAATH (jaisа Amazon/Flipkart)	EK-SERVER SE NAHI hota — MULTIPLE-SERVERS, MILके, handle karte hain


 Shopping-websites, "EK-SERVER ke 200-threads" pe DEPEND NAHI karti — wo "HORIZONTAL SCALING" (poое-poое-servers ko badhaна) use karte hain.


 */


/*Redis (shared-cache)	Jab MULTIPLE-SERVERS hon, "rate-limiting/sessions" jaisi cheezein, EK-COMMON-JAGAH (Redis) mein rakhi jaती hain, taaki SAB-SERVERS, SYNC mein rahen


User (IP: 192.168.1.5) → Request-1 bhejta hai
        ↓
Load-Balancer, is request ko → SERVER-1 pe bhej deta hai
        ↓
Server-1 ka apna Map: { "192.168.1.5" → count=1 }


SAME User (IP: 192.168.1.5) → Request-2 bhejta hai (thodi der baad)
        ↓
Load-Balancer, is baar → SERVER-2 pe bhej deta hai (round-robin ya kisi bhi logic se)
        ↓
Server-2 ka apna Map: { "192.168.1.5" → count=1 }   ← YE NAYA/FRESH COUNT HAI, Server-1 wala nahi mila

SAME User → Request-3, Request-4, Request-5...

Agar Load-Balancer, HAR-BAAR, request ko "Server-1, Server-2" mein ALTERNATE karta rahe:
Server-1 ka count: 1, 2, 3...
Server-2 ka count: 1, 2, 3...

User, PRACTICALLY, 6 requests bhej chuka hai (3-Server1 + 3-Server2),
LEKIN, KISI-BHI-SERVER ka individual-count, 3 se ZYADA NAHI dikh raha —
ISLIYE RATE-LIMIT KABHI TRIGGER HI NAHI HOGA!


Yehi ASLI PROBLEM hai — user, "6 requests" bhej chuka hai (jo humारে limit — 3-per-minute — se DOUBLE hai), lekin RATE-LIMIT, ISKO CATCH NAHI KAR PAYA, kyunki count, 2-ALAG-JAGAH (Server-1, Server-2) mein, ALAG-ALAG, chhota-chhota, track ho raha thа.

Solution — Redis, "EK SHARED, COMMON JAGAH" Hai
Server-1 →
SAB, EK-HI, "Redis" se baat karte hain (Redis, ek ALAG, independent server hai)
Server-2 →



Request-1 → Server-1 → Redis: "192.168.1.5" ka count check karo → 0 tha → +1 kiya → Redis mein 1
Request-2 → Server-2 → Redis: "192.168.1.5" ka count check karo → 1 tha (Redis se) → +1 kiya → Redis mein 2
Request-3 → Server-1 → Redis: count = 2 tha → +1 → Redis mein 3
Request-4 → Server-2 → Redis: count = 3 tha → LIMIT-CROSS → REJECT (429)*/
