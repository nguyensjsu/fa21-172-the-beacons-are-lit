package com.example;

import javax.persistence.metamodel.Metamodel;
import javax.servlet.http.HttpSession;

import com.example.helper.Message;
import com.example.user.UserModel;
import com.example.user.UserModelRepository;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import lombok.AllArgsConstructor;

/**
 * Main controller for customer service: Login, logout, register, reset
 * 
 * implement security here
 * api/login @Get
 * api/login @Post - login
 * api/login/reset @Get
 * api/login/reset/{userid} @Put
 * api/register @Get
 * api/register/{createduserid} @Post
 * - create the entry in ths sql db
 */
@RestController
public class CustomerController {

    private final UserModelRepository repository; //SQL repository containing all registered users on the site

    CustomerController(UserModelRepository repository){
        this.repository = repository; 
    }

    /**
     * Get the login page rendering only. Does not process login.
     * Debatable if this is part of the api
     * @return the page view name
     */
    @GetMapping("api/login")
    public String getLoginPage(Model model, HttpSession session){

        return "login"; 
    }

    /**
     * api/login/{email}/{password}
     * Login with the user's email and check password
     * Currently insecure, use https://www.baeldung.com/spring-security-basic-authentication
     * @return Message status if logged in successfully or not
     */
    @PostMapping("api/login/{email}/{password}")
    public Message login(@PathVariable String email, @PathVariable String password){
        UserModel userInfo = this.repository.findByEmail(email); 
        if(userInfo.getPassword().equals(password)){
            return new Message("success");
        }else{
            return new Message("failed"); 
        }
    } 


    /**
     * Registers a new user 
     * @param newUser user to be registered
     * @return a repository save.
     */
    @PostMapping("api/register/{email}")
    public UserModel register(@RequestBody UserModel newUser){
        //Check if there's an existing user
        if(this.repository.findByEmail(newUser.getEmail()) == null){
            System.out.println("This user already exists in the system!"); 
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User of this email already exists!"); 
        }
        
        return this.repository.save(newUser); 
    }

}
