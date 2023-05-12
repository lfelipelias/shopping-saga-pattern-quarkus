package com.lfefox.common.resource;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Felipe.Elias
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class OrderProductResource implements Serializable {

    private static final long serialVersionUID = 4476697649098179304L;

    @Schema(hidden = true)
    private OrderInfoResource orderInfo;

    @Schema(hidden = true)
    private Long orderProductId;

    @Schema(example= "700.00", description = "total of order")
    private BigDecimal price;

    @Schema(example= "3", description = "product ID")
    private Long productId;

    @Schema(hidden = true)
    private Date createDate;

}
