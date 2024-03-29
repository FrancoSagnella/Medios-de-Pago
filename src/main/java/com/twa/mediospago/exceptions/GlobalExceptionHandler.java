package com.twa.mediospago.exceptions;

import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.twa.mediospago.responses.ErrorResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {
    @ExceptionHandler(value = RuntimeException.class)
    protected ResponseEntity<ErrorResponse> runtimeExceptionHandler(RuntimeException ex){
        return new ResponseEntity<>(
                new ErrorResponse("BAD_REQUEST",
                        ex.getMessage(), LocalDateTime.now()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MPException.class)
    protected ResponseEntity<ErrorResponse> handleException(MPException exception) {

        return new ResponseEntity<>(
                new ErrorResponse("PAYMENT_REJECTED",
                        exception.getMessage(), LocalDateTime.now()), HttpStatus.PAYMENT_REQUIRED);
    }

    @ExceptionHandler(MPApiException.class)
    protected ResponseEntity<ErrorResponse> handleException(MPApiException exception) {

        System.out.printf(
                "MercadoPago Error. Status: %s, Content: %s%n",
                exception.getApiResponse().getStatusCode(), exception.getApiResponse().getContent());

        return new ResponseEntity<>(
                new ErrorResponse("PAYMENT_REJECTED",
                        exception.getApiResponse().getContent(), LocalDateTime.now()), HttpStatusCode.valueOf(exception.getApiResponse().getStatusCode()));
    }

    @ExceptionHandler(DecidirPaymentRejectedException.class)
    protected ResponseEntity<ErrorResponse> handleException(DecidirPaymentRejectedException exception) {
//        log.error(exception.getMessage(), exception);

        return new ResponseEntity<>(
                new ErrorResponse("PAYMENT_REJECTED",
                        exception.getMessage(), LocalDateTime.now()), HttpStatus.PAYMENT_REQUIRED);
    }

    @ExceptionHandler(DecidirBadRequestPaymentException.class)
    protected ResponseEntity<ErrorResponse> handleException(DecidirBadRequestPaymentException exception) {
//        log.error(exception.getMessage(), exception);

        return new ResponseEntity<>(
                new ErrorResponse("PAYMENT_ERROR",
                        exception.getMessage(), LocalDateTime.now()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DecidirBadRequestTokenException.class)
    protected ResponseEntity<ErrorResponse> handleException(DecidirBadRequestTokenException exception) {
//        log.error(exception.getMessage(), exception);

        return new ResponseEntity<>(
                new ErrorResponse("TOKEN_NOT_CREATED",
                        exception.getMessage(), LocalDateTime.now()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ServerErrorException.class)
    protected ResponseEntity<ErrorResponse> handleException(ServerErrorException exception) {
//        log.error(exception.getMessage(), exception);

        return new ResponseEntity<>(
                new ErrorResponse("DECIDIR_SERVER_ERROR",
                        exception.getMessage(), LocalDateTime.now()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
