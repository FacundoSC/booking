package org.faccordoba.springcloud.msvc.booking;

import org.faccordoba.springcloud.msvc.booking.dto.UserRegistrationDto;
import org.faccordoba.springcloud.msvc.booking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.logging.Logger;

@SpringBootApplication
public class BookingApplication {
    @Autowired
    UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(BookingApplication.class, args);
    }

    @Bean
    CommandLineRunner init() {
        Logger logger = Logger.getLogger(BookingApplication.class.getName());
        return args -> {
            UserRegistrationDto adminUser = new UserRegistrationDto("admin", "admin", "admin@example.com","admin");
            try {
                userService.loadUserByUsername(adminUser.email());
            } catch (UsernameNotFoundException e) {
                userService.save(adminUser);
            }
            logger.info("BookingApplication started successfully!");
            logger.info("Admin user: " + adminUser.email());

        };
    }

}
