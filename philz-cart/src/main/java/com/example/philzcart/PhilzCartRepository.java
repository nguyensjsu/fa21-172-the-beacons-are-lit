package com.example.philzcart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

interface PhilzCartRepository extends JpaRepository<PhilzCart, Long> {
    PhilzCart findByUserId(String userid);
}
