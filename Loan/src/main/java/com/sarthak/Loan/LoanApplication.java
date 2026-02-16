package com.sarthak.Loan;

import com.sarthak.Loan.dto.LoansContactInfoDto;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@EnableConfigurationProperties(value = {LoansContactInfoDto.class})
@EnableKafka
@OpenAPIDefinition(
		info = @Info(
				title = "Loans microservice REST API Documentation",
				description = "CentralBank Loans microservice REST API Documentation",
				version = "v1",
				contact = @Contact(
						name = "Sarthak Rastogi",
						email = "sarthakrastogi102@gmail.com",
						url = "https://www.linkedin.com/in/sarthak-rastogi-47bb11256"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "CentralBank Loans microservice REST API Documentation",
				url = "http://localhost:8090/swagger-ui.html"
		)
)
public class LoanApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoanApplication.class, args);
	}

}
