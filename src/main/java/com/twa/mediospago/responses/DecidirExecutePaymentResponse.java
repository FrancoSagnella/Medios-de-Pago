package com.twa.mediospago.responses;

import com.twa.mediospago.models.DecidirStatusDetails;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DecidirExecutePaymentResponse {
    private Long id;
    private String status;
    private String token;
    private DecidirStatusDetails status_details;
    private String establishment_name;
    private Integer bin;
    private String card_brand;
    private Integer site_id;
    private String date;
}
