package com.sarthak.Cards;

import com.sarthak.Cards.dto.CardsContactInfoDto;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@EnableConfigurationProperties(value = {CardsContactInfoDto.class})
@EnableKafka
@OpenAPIDefinition(
		info = @Info(
				title = "Cards microservice REST API Documentation",
				description = "CentralBank Cards microservice REST API Documentation",
				version = "v1",
				contact = @Contact(
						name = "Sarthak Rastogi",
						email = "sarthakrastogi102@gmail.com",
						url = "https://www.linkedin.com/in/sarthak-rastogi-47bb11256"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "CentralBank Cards microservice REST API Documentation",
				url = "http://localhost:9000/swagger-ui.html"
		)
)
public class CardsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CardsApplication.class, args);
	}

}
