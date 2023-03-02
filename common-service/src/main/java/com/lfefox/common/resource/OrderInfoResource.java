package com.lfefox.common.resource;

import com.lfefox.common.enums.TransactionEventTypeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Felipe.Elias
 */
@Data
@NoArgsConstructor
public class OrderInfoResource {

    private Long orderId;
    private Date createDate;
    private BigDecimal totalOrder;
    private String status;
    private Long  statusId;
    private TransactionEventTypeEnum transactionEventType;
    private Boolean shouldFail = Boolean.FALSE;

    private List<OrderProductModel> orderProductModels;

}
