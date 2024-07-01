package com.squad22podA.task_mgt.service.impl;

import com.squad22podA.task_mgt.payload.request.EmailDetails;
import com.squad22podA.task_mgt.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    @Value("$spring.mail.username")
    private String senderEmail;


    @Override
    public void sendEmailAlert(EmailDetails emailDetails) {

        try {

            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

            simpleMailMessage.setFrom(senderEmail);
            simpleMailMessage.setTo(emailDetails.getRecipient());
            simpleMailMessage.setText(emailDetails.getMessageBody());
            simpleMailMessage.setSubject(emailDetails.getSubject());

            javaMailSender.send(simpleMailMessage);
            System.out.println("Mail sent successfully!!");

        }catch (MailException exception){
            throw new RuntimeException(exception);
        }
    }
}
