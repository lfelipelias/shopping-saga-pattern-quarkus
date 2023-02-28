package com.lfefox.common.resource;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Felipe.Elias
 */
@Data
@NoArgsConstructor
public class OrderProductModel {
    private OrderResource order;
    private ProductModel product;
}
