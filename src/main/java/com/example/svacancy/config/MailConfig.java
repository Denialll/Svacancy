package com.example.svacancy.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

    @Bean
    public JavaMailSender getMailSender(){
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

        javaMailSender.setHost("smtp.gmail.com");
        javaMailSender.setPort(465);
        javaMailSender.setUsername("den4ic369@gmail.com");
        javaMailSender.setPassword("vnwsczzijjrjvdkb");

        Properties properties = javaMailSender.getJavaMailProperties();

        properties.put("mail.smtp.ssl.enable", true);

        return javaMailSender;
    }
}
