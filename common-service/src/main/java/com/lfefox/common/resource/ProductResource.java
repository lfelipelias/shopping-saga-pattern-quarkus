package com.lfefox.common.resource;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.*;

import java.util.Date;

/**
 * Felipe.Elias
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ProductResource {

    private Long productId;
    private String name;
    private String status;
    private Long  statusId;
    private Long orderId;
    private Date createDate;
}
