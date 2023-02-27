package com.lfefox.common.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Felipe.Elias
 */
@Data
@NoArgsConstructor
public class Product {

    private Long productId;
    private String name;
    private String status;
    private Long  statusId;
    private Long orderId;

}
