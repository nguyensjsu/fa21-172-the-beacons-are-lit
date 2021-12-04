package com.example.philzcart;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface PhilzCartRepository extends JpaRepository<PhilzCart, Long> {
    PhilzCart findByUserId(String userid);
}
