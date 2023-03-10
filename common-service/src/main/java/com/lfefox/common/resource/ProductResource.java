package com.lfefox.common.resource;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Felipe.Elias
 */
@Data
@NoArgsConstructor
public class ProductResource {

    private Long productId;
    private String name;
    private String status;
    private Long  statusId;
    private Long orderId;
    private Date createDate;
}
