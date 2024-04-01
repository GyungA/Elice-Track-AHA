package com.secondproject.shoppingproject.user.dto.response;

import com.secondproject.shoppingproject.common.ResponseCode;
import com.secondproject.shoppingproject.common.ResponseDto;
import com.secondproject.shoppingproject.common.ResponseMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class SignInResponse extends ResponseDto {

    //TODO : token 및 expirationTime;
    private String token;
    private int expirationTime;

    //TODO : token 받아서 주입, expiration Time 논의해서 설정 아마 30분 또는 1시간
//    private SignInResponse() {
//        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
//    }
    private SignInResponse(String token, int expirationTime) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.token = token;
        this.expirationTime = expirationTime;
    }
    //    public static ResponseEntity<SignInResponse> success(String token) {
//        SignInResponse result = new SignInResponse(token, 60*60*1000);
//        return ResponseEntity.ok().body(result);
//    }
    public static ResponseEntity<SignInResponse> success(String token, int expirationTime) {
        SignInResponse result = new SignInResponse(token, expirationTime);
        return ResponseEntity.ok().body(result);
    }

    public static ResponseEntity<ResponseDto> signInFailed() {
        ResponseDto result = new ResponseDto(ResponseCode.SIGN_IN_FAILED,
                ResponseMessage.SIGN_IN_FAILED);

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
    }

}
