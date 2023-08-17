package com.twa.mediospago.models;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DecidirCard {
    public String card_number;
    public String card_expiration_month;
    public String card_expiration_year;
    public String security_code;
    public String card_holder_name;
    public Identification card_holder_identification;

}
