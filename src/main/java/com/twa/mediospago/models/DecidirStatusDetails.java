package com.twa.mediospago.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DecidirStatusDetails {
    private String ticket;
    private String card_authorization_code;
    private String address_validation_code;
    private DecidirPaymentError error;
}
