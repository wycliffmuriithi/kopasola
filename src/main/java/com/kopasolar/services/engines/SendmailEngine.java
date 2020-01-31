package com.kopasolar.services.engines;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.List;

/**
 * Class name: SendmailEngine
 * Creater: wgicheru
 * Date:1/27/2020
 */
@Service
public class SendmailEngine {
    @Autowired
    public JavaMailSender emailSender;
    private static final Logger LOGGER = Logger.getLogger(SendmailEngine.class);

    @Async
    public void sendEmail(String subject,String body,String sendto) {

        MimeMessage message = emailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
//            String[] sendto = {"njuguna.david@ekenya.co.ke", "muriithi.wycliff@ekenya.co.ke", "mungai.john@ekenya.co.ke", "gicheha.john@ekenya.co.ke"};
//            String[] sendto={"muriithi.wycliff@ekenya.co.ke"};
            helper.setTo(sendto);
            helper.setSubject(subject);
            helper.setText(body);

            LOGGER.info(String.format("Sending mail %n%s%n%s%n to %s ",subject,body,sendto));
            emailSender.send(message);
        } catch (Exception ex) {
            LOGGER.error(ex);
        }
    }
}
