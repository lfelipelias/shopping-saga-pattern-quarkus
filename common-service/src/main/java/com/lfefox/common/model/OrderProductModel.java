package com.lfefox.common.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Felipe.Elias
 */
@Data
@NoArgsConstructor
public class OrderProductModel {
    private Order order;
    private Product product;
}
