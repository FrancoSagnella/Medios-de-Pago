package com.twa.mediospago.exceptions;

import com.twa.mediospago.responses.DecidirExecutePaymentResponse;

public class DecidirPaymentRejectedException extends RuntimeException{
    public DecidirPaymentRejectedException(DecidirExecutePaymentResponse res) {
        super("Error description: "+res.getStatus_details().getError().getReason().getDescription());
    }
}
