package com.twa.mediospago.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DecidirPaymentError {
    private String type;
    private DecidirPaymentErrorReason reason;
}
