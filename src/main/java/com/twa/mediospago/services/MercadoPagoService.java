package com.twa.mediospago.services;

import com.mercadopago.client.common.IdentificationRequest;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.payment.PaymentCreateRequest;
import com.mercadopago.client.payment.PaymentPayerRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import com.twa.mediospago.requests.MPPaymentRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class MercadoPagoService {

    ///Realiza el pago con los datos de la estructura MPPaymentRequest mediante el JDK de mercado pago
    public String createPayment(MPPaymentRequest payment) throws MPException, MPApiException {
        PaymentClient client = new PaymentClient();

        PaymentCreateRequest createRequest =
                PaymentCreateRequest.builder()
                        .transactionAmount(new BigDecimal(payment.getTransactionAmount()))
                        .token(payment.getToken())
                        .description(payment.getDescription())
                        .installments(payment.getInstallments())
                        .binaryMode(true)
                        .paymentMethodId(payment.getPaymentMethodId())
                        .payer(PaymentPayerRequest.builder().email(payment.getPayer().getEmail())
                                .identification(IdentificationRequest.builder()
                                        .type(payment.getPayer().getIdentification().getType())
                                        .number(payment.getPayer().getIdentification().getNumber()).build())
                                .build())
                        .build();

        Payment paymentResponse = client.create(createRequest);

        return paymentResponse.getResponse().getContent();
    }
}
