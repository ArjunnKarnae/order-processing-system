package com.orderprocessing.inventory.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

@Configuration
public class AuditingConfig {

    @Bean
    public AuditorAware<String> auditorAware(){
        return (() -> {
            return Optional.ofNullable("INVENTORY-SERVICE-API");
        });
    }
}
