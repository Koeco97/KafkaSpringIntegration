package Kafka.KafkaSpringIntegration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.kafka.dsl.Kafka;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Date;

@Configuration
@EnableIntegration
public class CustomFlow {

    private final ConsumerFactory consumerFactory;
    private final KafkaTemplate<String, MessageSample> kafkaTemplate;

    @Autowired
    public CustomFlow(ConsumerFactory<String, MessageSample> consumerFactory, KafkaTemplate<String, MessageSample> kafkaTemplate) {
        this.consumerFactory = consumerFactory;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Bean
    IntegrationFlow fromKafka() {
        return IntegrationFlows.from(Kafka.messageDrivenChannelAdapter(consumerFactory, "topic1"))
                .wireTap(s -> s.handle(n -> {
                    MessageSample modifiedMessage = (MessageSample) n.getPayload();
                    modifiedMessage.setHandledTimestamp(new Date().toString());
                }))
                .handle(Kafka.outboundChannelAdapter(kafkaTemplate).topic("topic2"))
                .get();
    }
}
