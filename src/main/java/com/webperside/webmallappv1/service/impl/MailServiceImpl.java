package com.webperside.webmallappv1.service.impl;

import com.webperside.webmallappv1.dto.MailDto;
import com.webperside.webmallappv1.service.MailService;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailServiceImpl implements MailService {
    //SMTP

    @Override
    public void send(MailDto mailDto) {
        Properties props = new Properties();

        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        Session session = Session.getInstance(props,new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("webperside.org@gmail.com","#1nc0rr3ct");
            }
        });

        try {

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("webperside.org@gmail.com"));
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(mailDto.getTo()));
            message.setSubject(mailDto.getSubject());
            message.setText(mailDto.getBody());

            Transport.send(message);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
