package com.orderprocessing.inventory.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.orderprocessing.inventory.events.InventoryReservationFailedEvent;
import com.orderprocessing.inventory.events.InventoryReservedEvent;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootStrapServers;

    @Value("${spring.kafka.producer.topic-name}")
    private String topicName;

    private Map<String, Object> configs(){
        Map<String, Object> configs = new HashMap<>();
        configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServers);
        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return configs;
    }

    @Bean
    public ObjectMapper producerObjectMapper(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return objectMapper;
    }

    @Bean
    public ProducerFactory<String, InventoryReservedEvent> inventoryReservedEventProducerFactory(){
        JsonSerializer<InventoryReservedEvent> serializer = new JsonSerializer<>(producerObjectMapper());
        serializer.setAddTypeInfo(false);
        return new DefaultKafkaProducerFactory<String, InventoryReservedEvent>(configs(), new StringSerializer(), serializer);

    }

    @Bean(name = "inventoryReservedEventKafkaTemplate")
    public KafkaTemplate<String, InventoryReservedEvent> inventoryReservedEventKafkaTemplate(){
        return new KafkaTemplate<String, InventoryReservedEvent>(inventoryReservedEventProducerFactory());
    }

    @Bean
    public ProducerFactory<String, InventoryReservationFailedEvent> inventoryReservationFailedEventProducerFactory(){
        JsonSerializer<InventoryReservationFailedEvent> serializer = new JsonSerializer<>(producerObjectMapper());
        serializer.setAddTypeInfo(false);
        return new DefaultKafkaProducerFactory<String, InventoryReservationFailedEvent>(configs(), new StringSerializer(), serializer);

    }

    @Bean(name = "inventoryReservationFailedEventKafkaTemplate")
    public KafkaTemplate<String, InventoryReservationFailedEvent> inventoryReservationFailedEventKafkaTemplate(){
        return new KafkaTemplate<String, InventoryReservationFailedEvent>(inventoryReservationFailedEventProducerFactory());
    }
}
