package com.example.philzcart;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PhilzCartRepository extends JpaRepository<PhilzCart, Long> {
    PhilzCart findByUsername(String username);
}
