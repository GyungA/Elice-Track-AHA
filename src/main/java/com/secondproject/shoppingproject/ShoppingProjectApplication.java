package com.secondproject.shoppingproject;

import com.secondproject.shoppingproject.category.repository.CategoryRepository;
import com.secondproject.shoppingproject.product.entity.ProductRepository;
import com.secondproject.shoppingproject.user.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableJpaAuditing
@SpringBootApplication
public class ShoppingProjectApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShoppingProjectApplication.class, args);
    }

    @Bean
    @Profile("local")
    public DataInit stubDataInit(UserRepository userRepository, ProductRepository productRepository, CategoryRepository categoryRepository, PasswordEncoder passwordEncoder) {
        return new DataInit(userRepository, productRepository, categoryRepository,passwordEncoder);
    }
}
