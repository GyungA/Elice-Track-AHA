package com.secondproject.shoppingproject.common;

public interface ResponseCode {


    //HTTP STATUS 200
    String SUCCESS = "SU";

    //HTTP STATUS 400
    String VALIDATION_FAILED = "VF";
    String DUPLICATE_EMAIL = "DE";
    String DUPLICATED_TEL_NUMBER = "DT";

    //HTTP STATUS 401
    String SIGN_IN_FAILED = "SF";
    String AUTHORIZATION_FAIL = "AF";

    //HTTP STATUS 403
    String NO_PERMISSION = "NP";

    //HTTP STATUS 500
    String DATABASE_ERROR = "DBE";

}
