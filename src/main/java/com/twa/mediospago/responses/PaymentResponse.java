package com.twa.mediospago.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PaymentResponse {
    private Long id; // Este debería ser el id del pago en nuestra base
    private Long externalPaymentId; // Este es el id del pago en mercadopago o decidir
    private String paymentMethodId;
    private String status;
}
