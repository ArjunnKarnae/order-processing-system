package com.orderprocessing.orders;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.servers.Servers;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@OpenAPIDefinition(
		info = @Info(
				title = "OrderServiceAPI",
				description = "API for managing the Orders",
				contact = @Contact(name = "Mallikarjun")),
		servers = {
				@Server(url = "http://localhost:8080/api/orders", description = "Localhost url for Orders Service API")
		}
)
@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class OrderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
	}

}
