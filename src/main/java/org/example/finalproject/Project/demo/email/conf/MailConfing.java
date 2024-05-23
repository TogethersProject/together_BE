package org.example.finalproject.Project.demo.email.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;

public class MailConfing {


    @Configuration
    @EnableTransactionManagement
    public class Mailconfig {

        @Value("${mail.host}")
        private String host;

        @Value("${mail.port}")
        private int port;

        @Value("${mail.username}")
        private String username;

        @Value("${mail.password}")
        private String password;

        @Value("${mail.javaMailProperties.protocol}")
        private String protocol;

        @Value("${mail.javaMailProperties.auth}")
        private String auth;

        @Value("${mail.javaMailProperties.enable}")
        private String enable;

        @Value("${mail.javaMailProperties.debug}")
        private String debug;

        @Value("${mail.javaMailProperties.ssl.trust}")
        private String sslTrust;

        @Value("${mail.javaMailProperties.ssl.protocols}")
        private String sslProtocols;

        @Bean
        public JavaMailSender javamailSender() {
            try {
                JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
                mailSender.setHost(host);
                mailSender.setUsername(username);
                mailSender.setPassword(password);
                mailSender.setPort(port);
                mailSender.setDefaultEncoding("UTF-8");


                Properties javaMailProperties = new Properties();
                javaMailProperties.put("mail.transport.protocol", protocol);
                javaMailProperties.put("mail.smtp.auth", auth);
                javaMailProperties.put("mail.smtp.starttls.enable", enable);
                javaMailProperties.put("mail.debug", debug);
                javaMailProperties.put("mail.smtp.ssl.trust", sslTrust);
                javaMailProperties.put("mail.smtp.ssl.protocols", sslProtocols);

                mailSender.setJavaMailProperties(javaMailProperties);

                return mailSender;
            } catch (Exception e) {
                throw new RuntimeException("빈 초기화 실패");
            }
        }
    }
}
