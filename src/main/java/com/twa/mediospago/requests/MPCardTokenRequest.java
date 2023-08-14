package com.twa.mediospago.requests;

import com.twa.mediospago.models.MPCardHolder;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MPCardTokenRequest {
    private String cardNumber;
    private MPCardHolder cardholder;
    private String expirationMonth;
    private String expirationYear; //El mes se pone el numero Ej: 11
    private String securityCode; //El año va completo Ej: 2025
}
