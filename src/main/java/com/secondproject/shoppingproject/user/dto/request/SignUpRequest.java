package com.secondproject.shoppingproject.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SignUpRequest {

	@NotBlank
	@Pattern(regexp = "^[0-9A-Za-z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])+[.][a-zA-Z]{2,3}$")
	private String email;

	@NotBlank
	@Size(min = 8 , max = 20)
	private String password;

	@NotBlank
	private String name;

	@NotBlank
	@Size(min=7,max=7)
	private String birthdate;

	private String address;

	private String phone;

}
