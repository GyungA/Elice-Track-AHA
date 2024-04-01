package com.secondproject.shoppingproject.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.secondproject.shoppingproject.cart.entity.Cart;
import com.secondproject.shoppingproject.global.entity.BaseEntity;
import com.secondproject.shoppingproject.order.entity.Order;
import com.secondproject.shoppingproject.product.entity.Product;
import com.secondproject.shoppingproject.user.constant.Grade;
import com.secondproject.shoppingproject.user.constant.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
public class User extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    Long user_id;

    @Column(name="email", nullable = false, unique = true)
    String email;

    @Column(name="password", nullable = false)
  //  @Size(min=8, max=20)
    String password;

    @Column(name="name", nullable = false)
    String name;

    @Column(name="birthdate", nullable = false, length = 7)
    String birthdate;//생년월일 6자리와 주민 번호 앞자리 1개를 받음 => ex) 9806101

    @Column(name="status", nullable = false)
    boolean status = true;

    @Column(name="address", nullable = false)
    String address;

    @Column(name="phone", nullable = false)
    String phone;

    @Enumerated(EnumType.STRING)
    @Column(name = "grade", nullable = true,columnDefinition = "varchar(255) default 'BRONZE'")
    Grade grade = Grade.BRONZE;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = true,columnDefinition = "varchar(255) default 'USER'")
    Role role = Role.USER;

//    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonIgnore
//    List<Order> orders = new ArrayList<>();
//
//    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonIgnore
//    List<Cart> carts = new ArrayList<>();
//
//    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonIgnore
//    List<Product> products = new ArrayList<>();

    @Builder
    public User(String email,String password, String name, String birthdate, boolean status, String address, String phone, Grade grade, Role role){
        this.email=email;
        this.password=password;
        this.name=name;
        this.birthdate=birthdate;
        this.status=status;
        this.address=address;
        this.phone=phone;
        this.grade=grade;
        this.role=role;
    }

    @Builder
    public User(String email,String password, String name, String birthdate, boolean status, String address, String phone){
        this.email=email;
        this.password=password;
        this.name=name;
        this.birthdate=birthdate;
        this.status=status;
        this.address=address;
        this.phone=phone;
        this.grade=Grade.BRONZE;
        this.role=Role.USER;
    }
}
