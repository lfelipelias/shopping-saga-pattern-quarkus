package com.lfefox.common.resource;

import com.lfefox.common.enums.TransactionEventTypeEnum;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Felipe.Elias
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class OrderInfoResource {

    private Long orderId;
    private Long userId;
    private Date createDate;
    private BigDecimal totalOrder;
    private String status;
    private Long  statusId;
    private TransactionEventTypeEnum transactionEventType;
    private Boolean shouldFail = Boolean.FALSE;

    private List<OrderProductResource> orderProductResources;

}
