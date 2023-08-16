package com.twa.mediospago.exceptions;

import com.twa.mediospago.responses.DecidirExecutePaymentResponse;

public class NotFoundException extends RuntimeException{
    public NotFoundException(String msg) {
        super(msg);
    }
}
