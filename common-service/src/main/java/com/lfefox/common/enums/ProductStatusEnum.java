package com.lfefox.common.enums;

/**
 * Felipe.Elias
 */
public enum ProductStatusEnum {

    AVAILABLE(1L), SELL_IN_PROGRESS(2L), SOLD(3L);
    private final Long id;

    public Long getId(){ return id; }

    ProductStatusEnum(Long id){ this.id = id; }
}
