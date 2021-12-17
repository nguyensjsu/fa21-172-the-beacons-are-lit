package com.example.philzpayment;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

interface PaymentsCommandRepository extends CrudRepository<PaymentsCommand, Integer> {
    
}
