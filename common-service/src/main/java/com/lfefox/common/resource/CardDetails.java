package com.lfefox.common.resource;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
