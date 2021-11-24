package com.example.Restful;

import org.springframework.data.jpa.repository.JpaRepository;

interface PhilzRepository extends JpaRepository<PhilzCard, Long> {

    // Spring will use default stuff here (JpaRepository). However will need to use custom
    // findByCardNumber for this specific setup
    PhilzCard findByCardNumber(String cardNumber);
}
