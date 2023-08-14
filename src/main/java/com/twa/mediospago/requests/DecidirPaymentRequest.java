package com.twa.mediospago.requests;

import com.mercadopago.resources.payment.Payment;
import com.twa.mediospago.models.DecidirCard;
import com.twa.mediospago.models.DecidirCardHolderIdentification;
import com.twa.mediospago.models.DecidirPayment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DecidirPaymentRequest {
    private String card_number;
    private String card_expiration_month;
    private String card_expiration_year;
    private String security_code;
    private String card_holder_name;
    private DecidirCardHolderIdentification card_holder_identification;
    private String site_transaction_id;
    private String token;
    private Long payment_method_id;
    private String bin;
    private Long amount;
    private String currency;
    private Long installments;
    private String payment_type;
    private String[] sub_payments;

    public DecidirPayment toItem(){
        return new DecidirPayment(site_transaction_id, token, payment_method_id, bin, amount, currency, installments, payment_type, sub_payments);
    }

    public DecidirCard toCard(){
        return new DecidirCard(card_number, card_expiration_month, card_expiration_year, security_code, card_holder_name, card_holder_identification);
    }
}
