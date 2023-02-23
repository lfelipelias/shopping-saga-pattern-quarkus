package com.lfefox.order.payment.model;

import lombok.*;

/**
 * Felipe.Elias
 */
@Getter
@Setter
@ToString
@Builder
public class CardDetails {

    private String name;
    private String cardNumber;
    private Integer validUntilMonth;
    private Integer validUntilYear;
    private Integer cvv;

}
