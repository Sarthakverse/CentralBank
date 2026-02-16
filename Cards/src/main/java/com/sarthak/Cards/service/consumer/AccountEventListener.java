package com.sarthak.Cards.service.consumer;

import com.sarthak.Cards.constants.CardsConstants;
import com.sarthak.Cards.dto.event.AccountCreateEvent;
import com.sarthak.Cards.entity.Cards;
import com.sarthak.Cards.exception.CardAlreadyExistsException;
import com.sarthak.Cards.repository.CardsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class AccountEventListener {
    private final CardsRepository cardsRepository;

    @KafkaListener(
            topics = "account-created-topic",
            groupId = "cards-group"
    )
    @Transactional
    public void handleAccountCreated(AccountCreateEvent event) {

        Optional<Cards> existingCard =
                cardsRepository.findByMobileNumber(event.getMobileNumber());

        if(existingCard.isPresent()) {
            throw new CardAlreadyExistsException("Card already registered with given mobileNumber "+event.getMobileNumber());
        }

        Cards newCard = new Cards();
        long randomCardNumber = 100000000000L + new Random().nextInt(900000000);
        newCard.setCardNumber(Long.toString(randomCardNumber));
        newCard.setMobileNumber(event.getMobileNumber());
        newCard.setCardType(CardsConstants.CREDIT_CARD);
        newCard.setTotalLimit(CardsConstants.NEW_CARD_LIMIT);
        newCard.setAmountUsed(0);
        newCard.setAvailableAmount(CardsConstants.NEW_CARD_LIMIT);

        cardsRepository.save(newCard);


    }
}