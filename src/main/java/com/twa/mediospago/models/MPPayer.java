package com.twa.mediospago.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MPPayer {
    private String email;
    private Identification identification;
}
