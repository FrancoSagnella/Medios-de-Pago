package com.twa.mediospago.requests;

import com.mercadopago.client.cardtoken.CardTokenRequest;
import com.twa.mediospago.models.MPPayer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class MPPaymentRequest {
    private Integer transactionAmount;
    private String token;
    private CardTokenRequest card;
    private String description;
    private Integer installments;
    private String paymentMethodId;
    private MPPayer payer;
}
