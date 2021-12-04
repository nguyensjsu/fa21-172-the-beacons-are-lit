package com.example.philzcart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository) {

        return args -> {
            userRepository.save(new User("John", "email@email.com", "password", "token", "address", "mobile"));
            userRepository.findAll().forEach(user -> log.info("Preloaded " + user));
        };
    }
}