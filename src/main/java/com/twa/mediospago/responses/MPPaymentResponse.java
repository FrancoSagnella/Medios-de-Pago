package com.twa.mediospago.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class MPPaymentResponse {
    public Long id;
    public String status;
    public String status_detail;
    public String payment_method_id;
    public String payment_type_id;
    public String external_reference;
}
