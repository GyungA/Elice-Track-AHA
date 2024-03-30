package com.secondproject.shoppingproject.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Setter
@Getter
@NoArgsConstructor
public class SignInRequest {

    @NotBlank
    private String email;

    @NotBlank
    private String password;

}
