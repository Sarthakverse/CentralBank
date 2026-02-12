package com.sarthak.gatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class GatewayserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayserverApplication.class, args);
	}

	@Bean
	public RouteLocator centralBankRouteConfig(RouteLocatorBuilder routeLocatorBuilder) {
		return routeLocatorBuilder.routes()
				// ACCOUNTS
				.route("accounts-route", r -> r
						.path("/centralbank/accounts/**")
						.filters(f -> f
								.rewritePath("/centralbank/accounts/(?<segment>.*)", "/${segment}")
								.addResponseHeader("X-Response-Time",
										String.valueOf(System.currentTimeMillis()))
								.circuitBreaker(config -> config
										.setName("accountsCircuitBreaker")
										.setFallbackUri("forward:/fallback/accounts")
								)
						)
						.uri("lb://ACCOUNTS")
				)

				//  LOANS
				.route("loans-route", r -> r
						.path("/centralbank/loans/**")
						.filters(f -> f
								.rewritePath("/centralbank/loans/(?<segment>.*)", "/${segment}")
								.addResponseHeader("X-Response-Time",
										String.valueOf(System.currentTimeMillis()))
								.circuitBreaker(config -> config
										.setName("loansCircuitBreaker")
										.setFallbackUri("forward:/fallback/loans")
								)
						)
						.uri("lb://LOANS")
				)

				// CARDS
				.route("cards-route", r -> r
						.path("/centralbank/cards/**")
						.filters(f -> f
								.rewritePath("/centralbank/cards/(?<segment>.*)", "/${segment}")
								.addResponseHeader("X-Response-Time",
										String.valueOf(System.currentTimeMillis()))
								.circuitBreaker(config -> config
										.setName("cardsCircuitBreaker")
										.setFallbackUri("forward:/fallback/cards")
								)
						)
						.uri("lb://CARDS")
				)

				.build();


	}
}
