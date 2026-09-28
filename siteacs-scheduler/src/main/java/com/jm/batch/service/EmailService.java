package com.jm.batch.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import jakarta.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;


    //Ek Saath 100 Async-Threads Mat Chalाओ aur 100 naye-contractors hon, to 100 async-threads EK SAATH fire ho jाएngе — SMTP-server (Mailtrap/real) overload ho sakта hai.
    //Isसे thodа better hoगа, lekin genuinely bade-scale (1000+) ke liए, "Thread-Pool ki size limit karна" zaroori ho jाता hai — jo humне @Async mein customize kar sakте hain:
    @Async("emailExecutor")
    public void sendWelcomeEmail(String toEmail, String firstName) {
        try {
            Context context = new Context();
            context.setVariable("firstName", firstName);
            context.setVariable("email", toEmail);

            String htmlBody = templateEngine.process("welcome-email", context);

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setTo(toEmail);
            helper.setSubject("Welcome to SiteACS - Upload Your Badge Photo");
            helper.setText(htmlBody, true);   // true = HTML content

            mailSender.send(mimeMessage);
            logger.info("Welcome email sent to: {}", toEmail);

        } catch (Exception e) {
            logger.error("Failed to send welcome email to: {}", toEmail, e);
        }
    }
}