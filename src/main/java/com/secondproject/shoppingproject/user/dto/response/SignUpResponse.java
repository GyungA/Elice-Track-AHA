package com.secondproject.shoppingproject.user.dto.response;

import com.secondproject.shoppingproject.common.ResponseCode;
import com.secondproject.shoppingproject.common.ResponseDto;
import com.secondproject.shoppingproject.common.ResponseMessage;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
public class SignUpResponse extends ResponseDto {

	private SignUpResponse() {
		super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
	}

	public static ResponseEntity<SignUpResponse> success() {
		return ResponseEntity.ok(new SignUpResponse());
	}

	public static ResponseEntity<ResponseDto> duplicateEmail() {
		ResponseDto result = new ResponseDto(ResponseCode.DUPLICATE_EMAIL,
				ResponseMessage.DUPLICATE_EMAIL);
		return ResponseEntity.badRequest().body(result);
	}

	public static ResponseEntity<ResponseDto> duplicateTelNumber() {
		ResponseDto result = new ResponseDto(ResponseCode.DUPLICATED_TEL_NUMBER,
				ResponseMessage.DUPLICATED_TEL_NUMBER);
		return ResponseEntity.badRequest().body(result);
	}

}
