package com.example.Restful;

import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@RestController
@AllArgsConstructor // using this for constructor to instantiate repository
public class StarbucksCardController {

    private final StarbucksCardRepository repository;

    class Message {
        @Getter
        @Setter
        private String status;
    }
    // StarbucksCardController(StarbucksCardRepository repository)

    // Create a new card
    @PostMapping("/cards")
    StarbucksCard newCard() {
        // Create new card object
        StarbucksCard newCard = new StarbucksCard();

        // Generate random number for card code
        // Ideally you would check for collision
        Random random = new Random();
        int num = random.nextInt(900000000) + 100000000;
        int code = random.nextInt(900) + 100;

        // fill values in for card
        newCard.setCardNumber(String.valueOf(num));
        newCard.setCardCode(String.valueOf(code));
        newCard.setBalance(20.00);
        newCard.setActivated(false);
        newCard.setStatus("New Card");

        return this.repository.save(newCard);
    }

    // Get list of all cards
    @GetMapping("/cards")
    List<StarbucksCard> getAllCards() {
        return this.repository.findAll();
    }

    // Get details of a card
    @GetMapping("/card/{num}")
    StarbucksCard getCard(@PathVariable String num, HttpServletResponse response) {
        StarbucksCard card = this.repository.findByCardNumber(num);

        if (card == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error, Card Not Found!");
        }
        return card;
    }

    @PostMapping("/card/activate/{num}/{code}")
    StarbucksCard activate(@PathVariable String num, @PathVariable String code, HttpServletResponse response) {
        StarbucksCard card = repository.findByCardNumber(num); // get the specific card by num

        if (card == null) { // not found
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error, Card Not Found!");
        }

        if (card.getCardCode().equals(code)) { // if found & code matches, activate
            card.setActivated(true);
            this.repository.save(card);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error, Card Not Valid!");
        }

        return card;
    }

    @DeleteMapping("/cards")
    Message deleteAll() {
        this.repository.deleteAllInBatch();
        Message msg = new Message();
        msg.setStatus("All Cards Deleted!");
        return msg;

    }
}
