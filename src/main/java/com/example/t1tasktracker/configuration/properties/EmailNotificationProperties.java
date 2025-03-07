package com.example.t1tasktracker.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "app.notification.mail")
public record EmailNotificationProperties(String fromAddress,
                                          List<String> toAddresses,
                                          String subjectTemplate,
                                          String textTemplate) {
}
