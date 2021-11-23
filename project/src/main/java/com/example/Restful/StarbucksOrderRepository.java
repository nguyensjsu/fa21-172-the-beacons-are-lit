package com.example.Restful;

import org.springframework.data.jpa.repository.JpaRepository;

interface StarbucksOrderRepository extends JpaRepository<StarbucksOrder, Long> {

}
