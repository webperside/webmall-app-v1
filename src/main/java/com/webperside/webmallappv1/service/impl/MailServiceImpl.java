package com.webperside.webmallappv1.service.impl;

import com.webperside.webmallappv1.dto.MailDto;
import com.webperside.webmallappv1.service.MailService;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MailServiceImpl implements MailService {
    //SMTP

    @Override
    public void send(MailDto mailDto) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                sendUtil(mailDto);
            }
        });
        executorService.shutdown();
//        Thread th = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                sendUtil(mailDto);
//            }
//        });
//
//        th.start();
    }

    private void sendUtil(MailDto mailDto){
        final String from = "webperside.org@gmail.com";
        final String pass = "#1nc0rr3ct";

        Properties props = new Properties();

        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
//        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
//        props.put("mail.smtp.ssl.enable","true");

        Session session = Session.getInstance(props,new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, pass);
            }
        });

        try {

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
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
