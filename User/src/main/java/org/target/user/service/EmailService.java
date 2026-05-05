package org.target.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public String generateCode() {
        return String.valueOf(new Random().nextInt(900000) + 100000);
    }

    public void sendVerificationCode(String toEmail, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("CrazyShop Verification Code");
        message.setText("Your verification code is: " + code + "\n\nThis code will expire in 5 minutes.");

        mailSender.send(message);
    }
}