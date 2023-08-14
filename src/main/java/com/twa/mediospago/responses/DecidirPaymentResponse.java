package com.twa.mediospago.responses;

import com.twa.mediospago.models.DecidirStatusDetails;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DecidirPaymentResponse {
    private Long id;
    private String site_transaction_id;
    private String status;
    private DecidirStatusDetails status_details;
}
