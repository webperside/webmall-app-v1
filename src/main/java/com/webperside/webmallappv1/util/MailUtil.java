package com.webperside.webmallappv1.util;

import com.webperside.webmallappv1.context.ContextLogic;
import com.webperside.webmallappv1.dto.MailDto;
import com.webperside.webmallappv1.service.MailService;

public class MailUtil {

    private static final MailService mailService = ContextLogic.mailServiceInstance();

    public static void sendRegistrationEmail(String to, String code){
        String url = "http://localhost:8080/confirm-registration?code="+code;

        MailDto mail = new MailDto();
        mail.setTo(to);
        mail.setSubject("Confirm Registration");
//        mail.setBody("Use this link to confirm your profile : [LINK]".replaceAll("[LINK]",url));
        mail.setBody("Use this link to confirm your profile : " + url);
        mailService.send(mail);
    }
}
