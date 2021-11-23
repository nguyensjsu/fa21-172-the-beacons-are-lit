package com.example.starbucksapi;

import org.springframework.data.jpa.repository.JpaRepository;

interface StarbucksCardRepository extends JpaRepository<StarbucksCard, Long> {

    // Spring will use default stuff here (JpaRepository). However will need to use custom
    // findByCardNumber for this specific setup
    StarbucksCard findByCardNumber(String cardNumber);
}
