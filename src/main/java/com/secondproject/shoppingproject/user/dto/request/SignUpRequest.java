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
//    @Size(min=8, max=20)
    String password;

//	@NotBlank
//    @Size(min=8, max=20)
    //String password_check;

    @NotBlank
    String name;

    @NotBlank
    @Size(min = 6, max = 6)
    String birthdate;//생년월일 6자리와 주민 번호 앞자리 1개를 받음 => ex) 9806101 => 수정 : 남녀를 입력받는 것이 업으므로 생년월일만을 저장하게 함

    @NotBlank
    String address;

    @NotBlank
    String phone;

}
