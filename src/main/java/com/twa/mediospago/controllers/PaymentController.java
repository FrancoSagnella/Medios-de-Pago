package com.twa.mediospago.controllers;

import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.twa.mediospago.models.MPNotification;
import com.twa.mediospago.requests.DecidirPaymentRequest;
import com.twa.mediospago.requests.MPCheckoutProRequest;
import com.twa.mediospago.requests.MPPaymentRequest;
import com.twa.mediospago.responses.DecidirPaymentResponse;
import com.twa.mediospago.services.DecidirService;
import com.twa.mediospago.services.MercadoPagoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        var response = mercadoPagoService.createPayment(payment);

        //Aca deberia guardarse objeto payment

        //Habria que mappear bien la respuesta de esto
        return ResponseEntity.ok(response);
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

        //Aca deberia guardarse el objeto payment

        return ResponseEntity.ok(response);
    }

    //Devuelve la url que abre el form de pago de MP, una vez que se resuelve el pago, va a llegar a alguno de los dos endpoints de abajo
    @PostMapping(value = "/checkout-pro")
    public ResponseEntity<String> checkoutPro(@RequestBody MPCheckoutProRequest item) throws MPException, MPApiException {

        return ResponseEntity.ok(mercadoPagoService.createPreference(item));
    }

    //Aca llegan los backUrl de MP, esto calculo que no se va a usar, el backUrl en realidad deberia usarse para redirigir al front end del cliente.
    //Lo podemos usar para hacer pruebas locales.
    @GetMapping(value = "/respuesta-mercado-pago")
    public Void mercadoPagoBack(
            @RequestParam(value="collection_id") Long collection_id,
            @RequestParam(value="collection_status") String collection_status,
            @RequestParam(value="payment_id") Long payment_id,
            @RequestParam(value="status") String status,
            @RequestParam(value="external_reference") String external_reference,
            @RequestParam(value="payment_type") String payment_type,
            @RequestParam(value="merchant_order_id") Long merchant_order_id,
            @RequestParam(value="preference_id") String preference_id,
            @RequestParam(value="site_id") String site_id,
            @RequestParam(value="processing_mode") String processing_mode,
            @RequestParam(value="merchant_account_id") String merchant_account_id)
    {
        //Aca deberia guardarse el objeto payment

        System.out.println(status);
        System.out.println(payment_id);
        return null;
    }

    //Si al crear la preferencia se le manda notificationUrl (que lo comente), una vez se aprueba/rechaza el pago, MP manda post a esa url (que deberia ser este endpoint)
    //Pero lo comente y no funca porque lo estoy probando local y sino se rompe.
    @PostMapping(value = "/notificacion-mercado-pago")
    public Void mercadoPagoNotification(@RequestBody MPNotification param) {

        //Aca deberia guardarse el objeto payment

        //El status del pago se puede sacar de este getPayment
        System.out.println(mercadoPagoService.getPayment(param));
        return null;
    }
}
