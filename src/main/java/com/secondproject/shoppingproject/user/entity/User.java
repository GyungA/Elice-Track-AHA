package com.secondproject.shoppingproject.user.entity;


import com.secondproject.shoppingproject.user.constant.Grade;
import com.secondproject.shoppingproject.user.constant.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user")
@EntityListeners(AuditingEntityListener.class)
public class User {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "user_id")
	private long id;

	private String email;

	private String password;

	private String name;

	private String birthdate;

	@Enumerated(EnumType.STRING)
	private Grade grade;

	private boolean status;

	@CreatedDate
	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@LastModifiedDate
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	private String address;

	private String phone;

	@Enumerated(EnumType.STRING)
	private Role role;

	@Builder
	private User(String email, String password, String name, String birthdate, String address,
			String phone) {
		this.email = email;
		this.password = password;
		this.name = name;
		this.birthdate = birthdate;
		this.address = address;
		this.phone = phone;
	}
}
