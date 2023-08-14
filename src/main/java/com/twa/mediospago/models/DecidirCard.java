package com.twa.mediospago.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DecidirCard {
    public String card_number;
    public String card_expiration_month;
    public String card_expiration_year;
    public String security_code;
    public String card_holder_name;
    public DecidirCardHolderIdentification card_holder_identification;

}
