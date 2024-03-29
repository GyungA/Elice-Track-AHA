package com.secondproject.shoppingproject.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.secondproject.shoppingproject.cart.entity.Cart;
import com.secondproject.shoppingproject.global.entity.BaseEntity;
import com.secondproject.shoppingproject.order.entity.Order;
import com.secondproject.shoppingproject.product.entity.Product;
import com.secondproject.shoppingproject.user.Enum.Grade;
import com.secondproject.shoppingproject.user.Enum.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
@NoArgsConstructor
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
//    @Size(min=8, max=20)//비밀번호는 8자 이상 20자 이하로 입력
    String password;

    @Column(name="name", nullable = false)
    String name;

    @Column(name="birthdate", nullable = false, length = 7)
    String birthdate;//생년월일 6자리와 주민 번호 앞자리 1개를 받음 => ex) 9806101

    @Column(name="status", nullable = false)
    String status;

    @Column(name="address")
    String address;

    @Column(name="phone", nullable = false)
    String phone;

    @Enumerated(EnumType.STRING)
    @Column(name = "grade", nullable = true)
    private Grade grade = Grade.BRONZE;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role = Role.USER;

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

    //    public User(String email,String password, String name, String birthdate, String status, String address, String phone, Grade grade, Role role){
//        this.email=email;
//        this.password=password;
//        this.name=name;
//        this.birthdate=birthdate;
//        this.status=status;
//        this.address=address;
//        this.phone=phone;
//        this.grade=grade;
//        this.role=role;
//    }
    public User(String email,String password, String name, String birthdate, String status, String address, String phone, Grade grade, Role role){
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

    public User(String email,String password, String name, String birthdate, String status, String address, String phone){
        this.email=email;
        this.password=password;
        this.name=name;
        this.birthdate=birthdate;
        this.status=status;
        this.address=address;
        this.phone=phone;
    }
}
