package com.secondproject.shoppingproject;

import com.secondproject.shoppingproject.user.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ShoppingProjectApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShoppingProjectApplication.class, args);
    }

    @Bean
    @Profile("local")
    public DataInit stubDataInit(UserRepository userRepository) {
        return new DataInit(userRepository);
    }
}
