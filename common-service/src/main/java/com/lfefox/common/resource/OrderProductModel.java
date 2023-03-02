package com.lfefox.common.resource;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Felipe.Elias
 */
@Data
@NoArgsConstructor
public class OrderProductModel {
    private OrderInfoResource order;
    private ProductModel product;
}
