package com.orderprocessing.orders.config;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.orderprocessing.orders.dto.OrderPlacedEvent;
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
public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.topic-name}")
    private String topicName;

    public Map<String, Object> producerConfigs(){
        Map<String, Object> producerConfigs = new HashMap<>();
        producerConfigs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        producerConfigs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        producerConfigs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        //producerConfigs.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, true); - Use this if we want to use generic Json serializer
        return producerConfigs;
    }

    @Bean
    public ObjectMapper objectMapper(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        return objectMapper;
    }

   /* @Bean
    public ProducerFactory<String, Object> kafkaProducer(){
        JsonSerializer<Object> valueSerializer = new JsonSerializer<>(objectMapper());
        valueSerializer.setAddTypeInfo(true);

        return new DefaultKafkaProducerFactory<>(producerConfigs(), new StringSerializer(), valueSerializer);
       // return new DefaultKafkaProducerFactory<>(producerConfigs(), new StringSerializer(), new JsonSerializer<>(objectMapper()));
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate(){
        return new KafkaTemplate<>(kafkaProducer());
    }
    */
    @Bean
    public ProducerFactory<String, OrderPlacedEvent> orderPlacedEventProducerFactory(){
        JsonSerializer<OrderPlacedEvent> serializer = new JsonSerializer();
        serializer.setAddTypeInfo(false);
        return new DefaultKafkaProducerFactory<>(producerConfigs(), new StringSerializer(), serializer);
    }

    @Bean
    public KafkaTemplate<String, OrderPlacedEvent> orderPlacedEventKafkaTemplate(){
        return new KafkaTemplate<String, OrderPlacedEvent>(orderPlacedEventProducerFactory());

    }

    @Bean
    public KafkaAdmin.NewTopics topics(){
        return new KafkaAdmin.NewTopics(
                TopicBuilder.name(topicName).partitions(2).replicas(1).build()
        );
    }
}


