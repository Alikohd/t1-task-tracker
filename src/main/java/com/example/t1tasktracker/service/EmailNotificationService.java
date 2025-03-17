package com.example.t1tasktracker.service;

import com.example.t1tasktracker.configuration.properties.EmailNotificationProperties;
import com.example.t1tasktracker.dto.TaskNotificationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.etu.t1logstarter.aspect.annotation.LogBefore;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailNotificationService implements NotificationService {
    private final EmailNotificationProperties emailProps;
    private final JavaMailSender emailSender;

    @Override
    @LogBefore
    public void notifyTaskStatusUpdate(TaskNotificationDto taskNotification) {
        String subject = String.format(emailProps.subjectTemplate(), taskNotification.id());
        String text = String.format(emailProps.textTemplate(), taskNotification.id(), taskNotification.taskStatus());
        emailProps.toAddresses().forEach(email -> sendMessageAsync(email, subject, text));
    }

    @Async("emailTaskExecutor")
    protected void sendMessageAsync(String toEmail, String subject, String text) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(emailProps.fromAddress());
        mailMessage.setTo(toEmail);
        mailMessage.setSubject(subject);
        mailMessage.setText(text);

        try {
            emailSender.send(mailMessage);
            log.debug("Email sent to {}", toEmail);
        } catch (MailException e) {
            log.warn("Failed to send email to {}: {}", toEmail, e.getMessage());
        }
    }

}
