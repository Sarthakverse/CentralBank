package org.sarthak.accounts.service.client;

import org.sarthak.accounts.dto.CardsDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class CardsFallback implements CardsFeignClient{
   @Override
    public ResponseEntity<CardsDto> fetchCardDetails(String correlationId, String mobileNumber) {
       CardsDto fallback = new CardsDto();
       fallback.setMobileNumber(mobileNumber);
       fallback.setCardNumber("000000000000");
       fallback.setCardType("Service Unavailable");
       fallback.setTotalLimit(0);
       fallback.setAmountUsed(0);
       fallback.setAvailableAmount(0);

       return ResponseEntity.ok(fallback);
    }
}
