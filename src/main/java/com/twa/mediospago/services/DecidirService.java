package com.twa.mediospago.services;

import com.twa.mediospago.exceptions.DecidirBadRequestPaymentException;
import com.twa.mediospago.exceptions.DecidirBadRequestTokenException;
import com.twa.mediospago.exceptions.DecidirPaymentRejectedException;
import com.twa.mediospago.exceptions.ServerErrorException;
import com.twa.mediospago.models.DecidirCard;
import com.twa.mediospago.models.DecidirPayment;
import com.twa.mediospago.requests.PaymentRequest;
import com.twa.mediospago.responses.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class DecidirService {
    private final WebClient webClient;
    public DecidirService(WebClient webClient){
        this.webClient = webClient;
    }

    @Value("${public.token}")
    private String publicToken;
    @Value("${private.token}")
    private String privateToken;
    @Value("${decidir.url}")
    private String url;

    public String createToken(DecidirCard card){

        var response = webClient.post()
                .uri(url+"tokens")
                .headers(header -> header.add("apiKey", publicToken))
                .bodyValue(card)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, error -> error.bodyToMono(DecidirTokenErrorResponse.class).map(DecidirBadRequestTokenException::new))
                .onStatus(HttpStatusCode::is5xxServerError, error ->
                        Mono.error(new ServerErrorException(
                                "Service failed to respond", error.statusCode().value())))
                .bodyToMono(DecidirTokenResponse.class)
                .block();

        return response.getId();
    }
    public PaymentResponse executePayment(PaymentRequest request){
        // Creo el token
        DecidirCard tokenRequest = DecidirCard.builder()
                .card_number(request.getCard_number())
                .card_expiration_month(request.getCard_expiration_month())
                .card_expiration_year(request.getCard_expiration_year())
                .card_holder_name(request.getCard_holder_name())
                .card_holder_identification(request.getCard_holder_identification())
                .security_code(request.getSecurity_code())
                .build();
        String token = createToken(tokenRequest);

        // Creo el item
        DecidirPayment item = DecidirPayment.builder()
                .amount(request.getAmount())
                .bin(request.getBin())
                .currency("ARS")
                .installments(request.getInstallments())
                .payment_type("single")
                .payment_method_id(Long.valueOf(request.getPayment_method_id()))
                .site_transaction_id(request.getSite_transaction_id())
                .sub_payments(request.getSub_payments())
                .token(token)
                .build();

        var response = webClient.post()
                .uri(url+"payments")
                .headers(header -> header.add("apiKey", privateToken))
                .bodyValue(item)
                .retrieve()
                .onStatus(HttpStatus.BAD_REQUEST::equals, error -> error.bodyToMono(DecidirPaymentErrorResponse.class).map(DecidirBadRequestPaymentException::new))
                .onStatus(HttpStatus.PAYMENT_REQUIRED::equals, error -> error.bodyToMono(DecidirExecutePaymentResponse.class).map(DecidirPaymentRejectedException::new))
                .onStatus(HttpStatusCode::is5xxServerError, error ->
                        Mono.error(new ServerErrorException(
                                "Service failed to respond", error.statusCode().value())))
                .bodyToMono(DecidirExecutePaymentResponse.class)
                .block();

        //return new DecidirPaymentResponse(response.getId(), item.getSite_transaction_id(), response.getStatus(), response.getStatus_details());
        return new PaymentResponse(response.getId(), // aca debería ir el id de nuestra BBDD
                response.getId(), item.getPayment_method_id().toString(), response.getStatus());
    }
}
