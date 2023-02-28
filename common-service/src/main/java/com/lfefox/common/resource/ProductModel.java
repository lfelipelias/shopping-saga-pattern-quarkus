package com.lfefox.common.resource;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Felipe.Elias
 */
@Data
@NoArgsConstructor
public class ProductModel {

    private Long productId;
    private String name;
    private String status;
    private Long  statusId;
    private Long orderId;

}
