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
				.route(p -> p
						.path("/centralbank/accounts/**")
						.filters( f -> f.rewritePath("/centralbank/accounts/(?<segment>.*)","/${segment}")
								.addResponseHeader("X-Response-Time",
										String.valueOf(System.currentTimeMillis())))
						.uri("lb://ACCOUNTS"))
				.route(p -> p
						.path("/centralbank/loans/**")
						.filters( f -> f.rewritePath("/centralbank/loans/(?<segment>.*)","/${segment}")
								.addResponseHeader("X-Response-Time",
										String.valueOf(System.currentTimeMillis())))
						.uri("lb://LOANS"))
				.route(p -> p
						.path("/centralbank/cards/**")
						.filters( f -> f.rewritePath("/centralbank/cards/(?<segment>.*)","/${segment}")
								.addResponseHeader("X-Response-Time",
										String.valueOf(System.currentTimeMillis())))
						.uri("lb://CARDS")).build();


	}
}
