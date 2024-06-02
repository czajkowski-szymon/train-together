package pl.czajkowski.emailservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MessageListener {

    private final Logger LOGGER = LoggerFactory.getLogger("logger");
    private final EmailSenderService senderService;

    public MessageListener(EmailSenderService senderService) {
        this.senderService = senderService;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void listener(TrainingInvitationMessage message) {
        LOGGER.info("Message: " + message + "received");
        String body = "You just go an invitation from " + message.senderName() +
                " for a " + message.sport() + " training on " + message.date() + ":\n" + message.message();
        senderService.send(message.receiverEmail(), "Invitation", body);
    }
}
