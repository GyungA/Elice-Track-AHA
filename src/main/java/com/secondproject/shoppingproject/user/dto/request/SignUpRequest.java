package com.secondproject.shoppingproject.user.dto.request;

import com.secondproject.shoppingproject.user.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class SignUpRequest {

    @NotBlank
    @Pattern(regexp = "^[0-9A-Za-z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])+[.][a-zA-Z]{2,3}$")
    String email;

    @NotBlank
    @Size(min=8, max=20)
    String password;

    @NotBlank
    String name;

    @NotBlank
    @Size(min=7, max=7)
    String birthdate;//생년월일 6자리와 주민 번호 앞자리 1개를 받음 => ex) 9806101

    @NotBlank
    boolean status;

    @NotBlank
    String address;

    @NotBlank
    String phone;

    public User toEntity(){
        return new User(email,password, name, birthdate, status, address, phone);
    }

}
