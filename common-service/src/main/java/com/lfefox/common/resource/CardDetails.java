package com.lfefox.common.resource;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.*;

/**
 * Felipe.Elias
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class CardDetails {

    private String name;
    private String cardNumber;
    private Integer validUntilMonth;
    private Integer validUntilYear;
    private Integer cvv;

}
