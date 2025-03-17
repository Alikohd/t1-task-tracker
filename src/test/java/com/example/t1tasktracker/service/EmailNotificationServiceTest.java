package com.example.t1tasktracker.service;

import com.example.t1tasktracker.configuration.properties.EmailNotificationProperties;
import com.example.t1tasktracker.dto.TaskNotificationDto;
import com.example.t1tasktracker.entity.TaskStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailNotificationServiceTest {

    @InjectMocks
    private EmailNotificationService emailNotificationService;

    @Mock
    private EmailNotificationProperties emailProps;

    @Mock
    private JavaMailSender emailSender;

    @Test
    void notifyStatusUpdate_ShouldNotSendEmails_WhenAddressesEmpty() {
        TaskNotificationDto notificationDto = new TaskNotificationDto(1L, TaskStatus.DONE);
        when(emailProps.toAddresses()).thenReturn(Collections.emptyList());
        when(emailProps.subjectTemplate()).thenReturn("Task Update: %s");
        when(emailProps.textTemplate()).thenReturn("Task %s updated to %s");

        emailNotificationService.notifyTaskStatusUpdate(notificationDto);

        verify(emailSender, never()).send(any(SimpleMailMessage.class));
    }

    @Test
    void notifyStatusUpdate_ShouldSendEmails_WhenAddressesNotEmpty() {
        TaskNotificationDto notificationDto = new TaskNotificationDto(1L, TaskStatus.DONE);
        List<String> addresses = List.of("john@gmail.com", "ivan@mail.com");
        when(emailProps.toAddresses()).thenReturn(addresses);
        when(emailProps.fromAddress()).thenReturn("antonio@gmail.com");
        when(emailProps.subjectTemplate()).thenReturn("Task Update: %s");
        when(emailProps.textTemplate()).thenReturn("Task %s updated to %s");

        emailNotificationService.notifyTaskStatusUpdate(notificationDto);

        verify(emailSender, times(addresses.size())).send(any(SimpleMailMessage.class));
    }
}