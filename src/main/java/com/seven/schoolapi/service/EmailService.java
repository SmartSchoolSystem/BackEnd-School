package com.seven.schoolapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendSimpleEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        message.setFrom("diarreiadura13@gmail.com");

        mailSender.send(message);
    }

    public void sendPasswordResetEmail(String to, String token) {
        String link = "http://localhost:8080/auth/verify-reset?token=" + token;
        String message = "Para redefinir sua senha, use o código: " + token + " ou clique no link: " + link;
        sendSimpleEmail(to, "Recuperação de Senha", message);
    }
}
