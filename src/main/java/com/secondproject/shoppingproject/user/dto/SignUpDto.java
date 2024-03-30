package com.secondproject.shoppingproject.user.dto;

import com.secondproject.shoppingproject.user.entity.User;
import lombok.*;

@Data
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class SignUpDto {
    String email;
    String password;
    String name;
    String birthdate;//생년월일 6자리와 주민 번호 앞자리 1개를 받음 => ex) 9806101
    String status;
    String address;
    String phone;

//    public UserDto(String email,String password, String name, String birthdate, String status, String address, String phone, Grade grade, Role role){
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

    //    public User toEntity(){
//        return new User(email,password, name, birthdate, status, address, phone);
//    }
    public User toEntity(){
        return new User(email,password, name, birthdate, status, address, phone);
    }

}
