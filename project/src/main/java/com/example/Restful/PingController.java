package com.example.Restful;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {

    class Ping { // object meant to pass around data: this is the json file format. Hence class.
        private String test;

        public Ping(String msg) {
            this.test = msg;
        }

        public String getTest() { // Spring will call this.
            return this.test;
        }
    }

    @GetMapping("/ping")
    public Ping ping() {
        return new Ping("Starbucks API version 1.0 alive!");
    }

}
