package pl.czajkowski.emailservice;

import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {

    private final Logger LOGGER = LoggerFactory.getLogger("logger");
    private final JavaMailSender sender;

    public EmailSenderService(JavaMailSender sender) {
        this.sender = sender;
    }

    @Async
    public void send(String toEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("traintogether1337@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);

        sender.send(message);
        LOGGER.info("Mail sent successfully");
    }
}
