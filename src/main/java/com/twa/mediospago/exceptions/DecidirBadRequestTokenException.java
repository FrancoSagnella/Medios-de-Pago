package com.twa.mediospago.exceptions;

import com.twa.mediospago.responses.DecidirTokenErrorResponse;

public class DecidirBadRequestTokenException extends RuntimeException{
    private DecidirTokenErrorResponse res;
    public DecidirBadRequestTokenException(DecidirTokenErrorResponse res) {
        super(res.getError_type()+": "+res.getValidation_errors()[0].getCode()+", "+res.getValidation_errors()[0].getParam());
        this.res = res;
    }
}
