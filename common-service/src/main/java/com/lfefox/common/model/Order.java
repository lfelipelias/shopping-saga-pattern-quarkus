package com.lfefox.common.model;

import com.lfefox.common.enums.TransactionEventTypeEnum;
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
    private Long  statusId;
    private TransactionEventTypeEnum transactionEventType;

}
