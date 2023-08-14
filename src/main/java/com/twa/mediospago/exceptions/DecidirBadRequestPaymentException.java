package com.twa.mediospago.exceptions;

import com.twa.mediospago.responses.DecidirPaymentErrorResponse;
import lombok.Getter;

@Getter
public class DecidirBadRequestPaymentException extends RuntimeException{
    private DecidirPaymentErrorResponse res;
    public DecidirBadRequestPaymentException(DecidirPaymentErrorResponse res){
        super(res.getError_type()+": "+res.getValidation_errors()[0].getCode()+", "+res.getValidation_errors()[0].getParam());
        this.res = res;
    }
}
