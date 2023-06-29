package com.lfefox.common.enums;
/**
 * Felipe.Elias
 */
public enum OrderStatusEnum {

    CREATED(0L),
    IN_PROGRESS(1L),
    SUCCESS(2L),
    ERROR_PAYMENT(3L),
    ERROR_PRODUCT_NOT_AVAILABLE(4L),
    ERROR_PRODUCT(5L);
    private final Long id;

    public Long getId(){ return id; }

    OrderStatusEnum(Long id){ this.id = id; }
}
