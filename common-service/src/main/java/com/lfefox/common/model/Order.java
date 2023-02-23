package com.lfefox.common.model;

import com.lfefox.common.enums.TransactionEventTypeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Felipe.Elias
 */
@Data
@NoArgsConstructor
public class Order {

    private Long orderId;
    private String orderUuid;
    private BigDecimal price;
    private String status;
    private Long  statusId;
    private TransactionEventTypeEnum transactionEventType;
    private Boolean shouldFail = Boolean.FALSE;

}
