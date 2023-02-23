package com.lfefox.order.payment.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * Felipe.Elias
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Order {

    private Long id;
    private String orderUuid;
    private BigDecimal price;
    private String status;
    private String type;

}
