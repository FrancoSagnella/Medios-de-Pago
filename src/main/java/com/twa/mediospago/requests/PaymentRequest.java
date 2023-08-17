package com.twa.mediospago.requests;

import com.twa.mediospago.models.DecidirCard;
import com.twa.mediospago.models.DecidirPayment;
import com.twa.mediospago.models.Identification;
import com.twa.mediospago.models.MPPayer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class PaymentRequest {
    private Long amount;
    private String card_number;
    private String card_expiration_month;
    private String card_expiration_year;
    private String security_code;
    private String card_holder_name;
    private Identification card_holder_identification;
    private String currency;
    private String description;
    private Integer installments;
    private MPPayer payer;
    private String payment_method_id;
    private String payment_type;
    private String bin;
    private String site_transaction_id;
    private String[] sub_payments;
    private String token;
}



