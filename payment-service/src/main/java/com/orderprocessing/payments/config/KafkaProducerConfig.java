package com.orderprocessing.payments.config;



import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.orderprocessing.payments.events.PaymentFailedEvent;
import com.orderprocessing.payments.events.PaymentProcessedEvent;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public KafkaAdmin.NewTopics paymentTopics(){
        return new KafkaAdmin.NewTopics(
                TopicBuilder.name("payment-success-topic").partitions(2).replicas(1).build(),
                TopicBuilder.name("payment-failure-topic").partitions(2).replicas(1).build()
        );
    }

    public Map<String, Object> producerConfigs(){
        Map<String, Object> configsMap = new HashMap<>();
        configsMap.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configsMap.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configsMap.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return configsMap;
    }

    @Bean
    public ObjectMapper producerObjectMapper(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        return mapper;
    }

    @Bean(name = "paymentProcessedEventKafkaProducer")
    public ProducerFactory<String, PaymentProcessedEvent> paymentProcessedEventKafkaProducer(){
        JsonSerializer<PaymentProcessedEvent> serializer = new JsonSerializer<>(producerObjectMapper());
        return new DefaultKafkaProducerFactory<String, PaymentProcessedEvent>(producerConfigs(), new StringSerializer(), serializer);
    }

    @Bean(name = "paymentProcessedEventKafkaTemplate")
    public KafkaTemplate<String, PaymentProcessedEvent> paymentProcessedEventKafkaTemplate(){
        return new KafkaTemplate<>(paymentProcessedEventKafkaProducer());
    }

    @Bean(name = "paymentFailedEventProducerFactory")
    public ProducerFactory<String, PaymentFailedEvent> paymentFailedEventProducerFactory(){
        JsonSerializer<PaymentFailedEvent> serializer = new JsonSerializer<>(producerObjectMapper());
        return new DefaultKafkaProducerFactory<String, PaymentFailedEvent>(producerConfigs(), new StringSerializer(), serializer);
    }

    @Bean(name = "paymentFailedEventKafkaTemplate")
    public KafkaTemplate<String, PaymentFailedEvent> paymentFailedEventKafkaTemplate(){
        return new KafkaTemplate<>(paymentFailedEventProducerFactory());
    }
}
