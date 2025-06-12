package com.orderprocessing.inventory.config;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.orderprocessing.inventory.events.OrderPlacedEvent;
import com.orderprocessing.inventory.events.PaymentFailedEvent;
import com.orderprocessing.inventory.events.PaymentProcessedEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;


import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    public Map<String, Object> configsMap(){
        Map<String, Object> configsMap = new HashMap<>();
        configsMap.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configsMap.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configsMap.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        configsMap.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        return configsMap;
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }
/*
    @Bean
    public ConsumerFactory<String, Object> consumerFactory(){
        JsonDeserializer<Object> valueDeserializer = new JsonDeserializer<>(Object.class, objectMapper());
        valueDeserializer.setRemoveTypeHeaders(false);
        valueDeserializer.setUseTypeMapperForKey(false);

        Map<String, Class<?>> typeMappings = new HashMap<>();
        typeMappings.put("com.orderprocessing.orders.dto.OrderPlacedEvent", OrderPlacedEvent.class);

        valueDeserializer.setTypeMapper(new DefaultJackson2JavaTypeMapper() {{
            setTypePrecedence(TypePrecedence.TYPE_ID);
            setIdClassMapping(typeMappings);
            addTrustedPackages("*");
        }});

        return new DefaultKafkaConsumerFactory<>(configsMap(), new StringDeserializer(), valueDeserializer);

    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> concurrentKafkaListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<String, Object> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
            factory.setConsumerFactory(consumerFactory());
            return factory;
    }*/

    @Bean
    public ConsumerFactory<String, OrderPlacedEvent> orderPlacedEventConsumerFactory(){
        return createConsumerFactory(OrderPlacedEvent.class);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, OrderPlacedEvent> orderPlacedEventConcurrentKafkaListenerContainerFactory(){
        return createContainerFactory(OrderPlacedEvent.class);
    }

    @Bean(name = "paymentProcessedEventConsumerFactory")
    public ConsumerFactory<String, PaymentProcessedEvent> paymentProcessedEventConsumerFactory(){
        return createConsumerFactory(PaymentProcessedEvent.class);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, PaymentProcessedEvent> paymentProcessedContainerFactory(){
        return createContainerFactory(PaymentProcessedEvent.class);
    }

    @Bean(name = "paymentFailedEventConsumerFactory")
    public ConsumerFactory<String, PaymentFailedEvent> paymentFailedEventConsumerFactory(){
        return createConsumerFactory(PaymentFailedEvent.class);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, PaymentFailedEvent> paymentFailedContainerFactory(){
        return createContainerFactory(PaymentFailedEvent.class);
    }

    public <T> ConsumerFactory<String, T> createConsumerFactory(Class<T> targetType){
        JsonDeserializer<T> deserializer = new JsonDeserializer<>(targetType, objectMapper());
        deserializer.addTrustedPackages("com.orderprocessing.inventory.events");
        deserializer.setUseTypeMapperForKey(false);
        deserializer.setUseTypeHeaders(false);
        return new DefaultKafkaConsumerFactory<String, T>(configsMap(), new StringDeserializer(), deserializer);
    }

    public <T> ConcurrentKafkaListenerContainerFactory<String, T> createContainerFactory(Class<T> targetType){
        ConcurrentKafkaListenerContainerFactory<String, T> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(createConsumerFactory(targetType));
        return factory;
    }

}
