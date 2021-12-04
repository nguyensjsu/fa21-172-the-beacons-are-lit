package com.example.philzpayment;


import org.springframework.data.jpa.repository.JpaRepository;

interface PaymentsCommandRepository extends JpaRepository<PaymentsCommand, Integer> {
    
}
