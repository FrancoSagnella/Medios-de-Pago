package com.twa.mediospago.controllers;

import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.twa.mediospago.requests.DecidirPaymentRequest;
import com.twa.mediospago.requests.MPPaymentRequest;
import com.twa.mediospago.responses.DecidirPaymentResponse;
import com.twa.mediospago.services.DecidirService;
import com.twa.mediospago.services.MercadoPagoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/payment")
public class PaymentController {
    private final MercadoPagoService mercadoPagoService;
    private final DecidirService decidirService;

   /* REQUEST DE EJEMPLO {
        "transactionAmount":100,
            "card":{
        "cardNumber":"5031755734530604",
                "cardholder":{
            "name":"APRO", o cambiar por OTHE para rechazar pagos
                    "identification": {
                "type": "DNI",
                        "number": "12345678"
            }
        },
        "expirationYear":"2025",
                "expirationMonth":"11",
                "securityCode":"123"
    },
        "description":"descripcion",
            "installments":1,
            "paymentMethodId":"master",
            "payer":{
        "email":"test@test.com",
                "identification":{
            "type":"DNI",
                    "number":"44255742"
        }
    }
    }*/
    //FALTARIA TODA LA LOGICA DE CREAR EL PAGO PARA TENER REGISTRADO EN LA BBDD PROPIA DE MEDIOS DE PAGO
    @PostMapping("/mp")
    public ResponseEntity<String> executePayment(@RequestBody MPPaymentRequest payment) throws MPException, MPApiException {
        payment.setToken(mercadoPagoService.createCardToken(payment.getCard()));
        System.out.println(payment.getToken());
        return ResponseEntity.ok(mercadoPagoService.createPayment(payment));
    }

    /* REQUEST DE EJEMPLO
    {
        "card_number": "4507990000004905", o 4258210000474094 para pagos rechazados
        "card_expiration_month": "08",
        "card_expiration_year": "30",
        "security_code": "123",
        "card_holder_name": "John Doe",
        "card_holder_identification": {
            "type": "dni",
            "number": "25123456"
        },
        "site_transaction_id":"asassda",
        "payment_method_id": 1,
        "amount": 200,
        "currency": "ARS",
        "installments": 1,
        "payment_type": "single",
        "sub_payments": []


}
    */
    @PostMapping("/decidir")
    public ResponseEntity<DecidirPaymentResponse> payment(@RequestBody DecidirPaymentRequest request) {
        var token = decidirService.createToken(request.toCard());
        var response = decidirService.executePayment(token,
                request.toItem());

        return ResponseEntity.ok(response);
    }
}
