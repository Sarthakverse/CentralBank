package org.sarthak.accounts.dto;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;


// commented Because config values can change at runtime and Spring needs setters
// to update them, but records cannot be updated.
/*
    @ConfigurationProperties(prefix = "accounts")
    public record AccountsContactInfoDto(String message, Map<String, String> contactDetails, List<String> onCallSupport) {
    }
*/


@ConfigurationProperties(prefix = "accounts")
@Getter
@Setter
public class AccountsContactInfoDto {

    private String message;
    private Map<String, String> contactDetails;
    private List<String> onCallSupport;

}