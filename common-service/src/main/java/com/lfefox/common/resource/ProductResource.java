package com.lfefox.common.resource;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.*;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.io.Serializable;
import java.util.Date;

/**
 * Felipe.Elias
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ProductResource implements Serializable {

    private static final long serialVersionUID = 129215467827595767L;


    @Schema(example= "1", description = "product ID")
    private Long productId;

    @Schema(example= "1", description = "order ID")
    private Long orderId;

    @Schema(example= "1", description = "name")
    private String name;

    @Schema(example= "AVAILABLE", description = "status description")
    private String status;

    @Schema(example= "1", description = "status ID")
    private Long  statusId;

    @Schema(hidden = true)
    private Date createDate;
}
