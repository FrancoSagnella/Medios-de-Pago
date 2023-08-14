package com.twa.mediospago.services;

import com.twa.mediospago.exceptions.DecidirBadRequestPaymentException;
import com.twa.mediospago.exceptions.DecidirBadRequestTokenException;
import com.twa.mediospago.exceptions.DecidirPaymentRejectedException;
import com.twa.mediospago.exceptions.ServerErrorException;
import com.twa.mediospago.models.DecidirCard;
import com.twa.mediospago.models.DecidirPayment;
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
    public DecidirPaymentResponse executePayment(String token, DecidirPayment item){

        item.setToken(token);

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

        return new DecidirPaymentResponse(response.getId(), item.getSite_transaction_id(), response.getStatus(), response.getStatus_details());
    }
}
