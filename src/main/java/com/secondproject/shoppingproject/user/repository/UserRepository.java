package com.secondproject.shoppingproject.user.repository;

import com.secondproject.shoppingproject.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

	boolean existsByEmail(String email);

	boolean existsByPhone(String phone);

}
