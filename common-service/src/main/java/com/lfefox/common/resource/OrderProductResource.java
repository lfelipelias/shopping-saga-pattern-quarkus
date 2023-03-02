package com.lfefox.common.resource;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Felipe.Elias
 */
@Getter
@Setter
@NoArgsConstructor
public class OrderProductResource {

    private OrderInfoResource orderInfo;
    private Long orderProductId;
    private Date createDate;
    private BigDecimal price;
    private Long productId;

}
