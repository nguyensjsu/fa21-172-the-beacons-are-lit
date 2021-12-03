package com.example;

import com.example.user.UserModelRepository;

import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
/**
 * Main controller for customer service: Login, logout, register, reset
 * 
 * implement security here
 * api/login @Get
 * api/login/reset @Get
 * api/login/reset/{userid} @Put
 * api/register @Get
 * api/register/{createduserid} @Post
 * - create the entry in ths sql db
 */
public class CustomerController {
    private final UserModelRepository repository; 



}
