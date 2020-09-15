package Kafka.KafkaSpringIntegration;

import org.springframework.integration.endpoint.MessageProducerSupport;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CustomMessageHandler extends MessageProducerSupport implements MessageHandler {

    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        MessageSample messageSample = (MessageSample)message.getPayload();
        messageSample.setHandledTimestamp(LocalDateTime.now().toString());
        sendMessage(MessageBuilder.withPayload(messageSample).build());
    }
}
