package com.lfefox.model;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Order {

    private Long id;
    private String orderUuid;
    private BigDecimal price;
    private String status;

}
