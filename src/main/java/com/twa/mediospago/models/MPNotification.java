package com.twa.mediospago.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MPNotification {
    private Long id;
    private String type;
    private String date_created;
    private Long application_id;
    private Long user_id;
    private String action;
    private MPDataNotification data;
}
