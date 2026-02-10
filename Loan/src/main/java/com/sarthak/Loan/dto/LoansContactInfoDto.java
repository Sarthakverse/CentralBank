package com.sarthak.Loan.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

// commented Because config values can change at runtime and Spring needs setters
// to update them, but records cannot be updated.
/*
@ConfigurationProperties(prefix = "loans")
public record LoansContactInfoDto(String message, Map<String, String> contactDetails, List<String> onCallSupport) {
}
*/


@ConfigurationProperties(prefix = "loans")
@Getter
@Setter
public class LoansContactInfoDto {

    private String message;
    private Map<String, String> contactDetails;
    private List<String> onCallSupport;

}
