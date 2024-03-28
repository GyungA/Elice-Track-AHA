package com.secondproject.shoppingproject.user.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {
    @NotEmpty
    String email;

    @NotEmpty
    String password;
}
