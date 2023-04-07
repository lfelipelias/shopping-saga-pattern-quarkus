package com.lfefox.common.resource;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Felipe.Elias
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@RegisterForReflection
public class OrderProductResource {

    private OrderInfoResource orderInfo;
    private Long orderProductId;
    private Date createDate;
    private BigDecimal price;
    private Long productId;

}
