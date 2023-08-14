package com.twa.mediospago.responses;

import com.twa.mediospago.models.DecidirValidationErrors;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DecidirPaymentErrorResponse {
    private String error_type;
    private DecidirValidationErrors[] validation_errors;
}
