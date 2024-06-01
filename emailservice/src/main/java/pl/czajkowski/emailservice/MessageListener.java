package pl.czajkowski.emailservice;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MessageListener {

    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void listener(LoginRequest request) {
        System.out.println(request.username() + ", " + request.password());
    }
}
