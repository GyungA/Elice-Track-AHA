package com.secondproject.shoppingproject.user.repository;

import com.secondproject.shoppingproject.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAll();

    Optional<User> findById(Long id);

    User save(User user);

    void delete(User user);

    User findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByPassword(String password);

    boolean existsByPhone(String phone);
}
