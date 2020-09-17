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

@Configuration
@EnableIntegration
public class CustomFlow {
    private static final String TOPIC_FROM = "topic1";
    private static final String TOPIC_TO = "topic2";
    @Autowired
    private ConsumerFactory consumerFactory;
    @Autowired
    private KafkaTemplate kafkaTemplate;
    @Autowired
    private CustomMessageHandler customMessageHandler;

    
    @Bean
    IntegrationFlow fromKafka() {
        return IntegrationFlows.from(Kafka.messageDrivenChannelAdapter(consumerFactory, TOPIC_FROM))
                .handle(customMessageHandler)
                .handle(Kafka.outboundChannelAdapter(kafkaTemplate).topic(TOPIC_TO))
                .get();
    }
}
