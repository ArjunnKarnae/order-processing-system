package com.orderprocessing.inventory.config;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.orderprocessing.inventory.dto.OrderPlacedEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;


import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.mapping.DefaultJackson2JavaTypeMapper;
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
        configsMap.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        configsMap.put(JsonDeserializer.REMOVE_TYPE_INFO_HEADERS, false);
        return configsMap;
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }

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

        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), valueDeserializer);
        //JsonDeserializer<Object> deserializer = new JsonDeserializer<>();
       // deserializer.addTrustedPackages("com.orderprocessing.inventory.dto");
        //return new DefaultKafkaConsumerFactory<>(configsMap(), new StringDeserializer(), new JsonDeserializer<>(objectMapper()));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> concurrentKafkaListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<String, Object> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
            factory.setConsumerFactory(consumerFactory());
            return factory;
    }

}
