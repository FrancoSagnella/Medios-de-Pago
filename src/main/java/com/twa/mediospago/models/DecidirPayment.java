package com.twa.mediospago.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class DecidirPayment {
    private String site_transaction_id;
    private String token;
    private Long payment_method_id;
    private String bin;
    private Long amount;
    private String currency;
    private Long installments;
    //  private String description;
    private String payment_type;
    private String[] sub_payments;
}
