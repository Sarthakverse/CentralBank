package com.sarthak.Cards.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

// commented Because config values can change at runtime and Spring needs setters
// to update them, but records cannot be updated.
/*
@ConfigurationProperties(prefix = "cards")
public record CardsContactInfoDto(String message, Map<String, String> contactDetails, List<String> onCallSupport) {
}*/

@ConfigurationProperties(prefix = "cards")
@Getter
@Setter
public class CardsContactInfoDto {

    private String message;
    private Map<String, String> contactDetails;
    private List<String> onCallSupport;

}