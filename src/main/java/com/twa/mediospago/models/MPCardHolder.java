package com.twa.mediospago.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MPCardHolder {
    private String name;
    private MPIdentification identification;
}
