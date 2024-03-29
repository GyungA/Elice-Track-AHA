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

}
