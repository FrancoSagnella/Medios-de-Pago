package com.twa.mediospago.controllers;

import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.twa.mediospago.requests.MPPaymentRequest;
import com.twa.mediospago.services.MercadoPagoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class MercadoPagoController {
    private final MercadoPagoService mercadoPagoService;

    @PostMapping("/MP/payment")
    public ResponseEntity<String> executePayment(@RequestBody MPPaymentRequest payment) throws MPException, MPApiException {
        return ResponseEntity.ok(mercadoPagoService.createPayment(payment));
    }
}
