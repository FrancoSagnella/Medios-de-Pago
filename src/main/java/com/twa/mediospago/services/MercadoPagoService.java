package com.twa.mediospago.services;

import com.mercadopago.client.common.IdentificationRequest;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.payment.PaymentCreateRequest;
import com.mercadopago.client.payment.PaymentPayerRequest;
import com.mercadopago.client.preference.*;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.CardToken;
import com.mercadopago.resources.payment.Payment;
import com.mercadopago.resources.preference.Preference;
import com.twa.mediospago.models.MPNotification;
import com.twa.mediospago.requests.MPCardTokenRequest;
import com.twa.mediospago.requests.MPCheckoutProRequest;
import com.twa.mediospago.requests.MPPaymentRequest;
import com.twa.mediospago.responses.MPPaymentResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class MercadoPagoService {
    private final WebClient webClient;
    public MercadoPagoService(WebClient webClient){
        this.webClient = webClient;
    }
    @Value("${mercadopago.token}")
    private String token;
    @Value("${mercadopago.key}")
    private String publickey;

    public String createCardToken(MPCardTokenRequest cardParams) {

        var response = webClient.post()
                .uri("https://api.mercadopago.com/v1/card_tokens?public_key="+publickey)
                .bodyValue(cardParams)
                .retrieve()
                .bodyToMono(CardToken.class)
                .block();

        return response.getId();
    }

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

    public String createPreference(MPCheckoutProRequest item) throws MPException, MPApiException {
        Preference preference = new Preference();
        PreferenceClient client = new PreferenceClient();

        List<PreferenceItemRequest> items = new ArrayList<>();

        PreferenceItemRequest item1 = PreferenceItemRequest.builder()
                .title(item.getTitle())
                .quantity(item.getQuantity())
                .unitPrice(item.getUnitPrice())
                .categoryId(item.getCategoryId())
                .currencyId(item.getCurrencyId())
                .description(item.getDescription())
                .pictureUrl(item.getPictureUrl())
                .build();

        items.add(item1);

        PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest
                .builder()
                .success("http://localhost:8080/api/payment/respuesta-mercado-pago")
                .pending("http://localhost:8080/api/payment/respuesta-mercado-pago")
                .failure("http://localhost:8080/api/payment/respuesta-mercado-pago")
                .build();

        List<PreferencePaymentTypeRequest> excludedPaymentTypes = new ArrayList<>();
        excludedPaymentTypes.add(PreferencePaymentTypeRequest.builder().id("ticket").build());
        excludedPaymentTypes.add(PreferencePaymentTypeRequest.builder().id("debit_card").build());

        PreferenceRequest request = PreferenceRequest.builder()
                .items(items)
                .backUrls(backUrls)
                .autoReturn("approved")
                .binaryMode(true)
//                .notificationUrl(env.getProperty("back.url")+"MercadoPago/notificacion_mercadoPago/"+pago_id)
                .expires(false)
                .externalReference("a")
                .paymentMethods( PreferencePaymentMethodsRequest.builder().excludedPaymentTypes(excludedPaymentTypes).installments(1).build() )
                .build();

        return client.create(request).getSandboxInitPoint();
    }

    public String getPayment(MPNotification param){

        var response = webClient.post()
                .uri("https://api.mercadopago.com/v1/payments/"+param.getData().getId()+"?"+this.token)
                .retrieve()
//                .onStatus(HttpStatusCode::is4xxClientError, error -> error.bodyToMono(TokenErrorResponse.class).map(BadRequestTokenException::new))
//                .onStatus(HttpStatusCode::is5xxServerError, error ->
//                        Mono.error(new ServerErrorException(
//                                "Service failed to respond", error.statusCode().value())))
                .bodyToMono(MPPaymentResponse.class)
                .block();

        System.out.println("Pago de MercadoPago: "+response.toString());

        return response.toString();
    }
}
