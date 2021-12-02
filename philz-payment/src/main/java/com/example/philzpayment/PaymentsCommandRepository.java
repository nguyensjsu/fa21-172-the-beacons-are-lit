package com.example.philzapidb.api;


import org.springframework.data.jpa.repository.JpaRepository;

interface PaymentsCommandRepository extends JpaRepository<PaymentsCommand, Integer> {
    
}
