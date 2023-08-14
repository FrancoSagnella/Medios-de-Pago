package com.twa.mediospago.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DecidirPaymentErrorReason {
    private Long id;
    private String description;
    private String additional_description;
}
