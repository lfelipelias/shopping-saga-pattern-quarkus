package com.lfefox.common.resource;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lfefox.common.enums.TransactionEventTypeEnum;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.*;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.io.Serializable;
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
public class OrderInfoResource implements Serializable {

    private static final long serialVersionUID = -2423799531065893234L;

    @Schema(hidden = true)
    private Long orderId;

    @Schema(example= "2", description = "user ID")
    private Long userId;

    @Schema(hidden = true)
    private Date createDate;

    @Schema(example= "700.00", description = "total of order")
    private BigDecimal totalOrder;

    @Schema(hidden = true)
    private String status;

    @Schema(hidden = true)
    private Long  statusId;

    @Schema(hidden = true)
    private TransactionEventTypeEnum transactionEventType;

    @Schema(hidden = true)
    private Boolean shouldFail = Boolean.FALSE;

    private List<OrderProductResource> orderProductResources;

}
