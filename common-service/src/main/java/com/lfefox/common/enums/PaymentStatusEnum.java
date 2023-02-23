package com.lfefox.common.enums;
/**
 * Felipe.Elias
 */
public enum PaymentStatusEnum {

    IN_PROGRESS(1L), PAID(2L), ERROR_PROCESSING_PAYMENT(3L), REFUND_DUE_COMPENSATION(4L);
    private final Long id;

    public Long getId(){ return id; }

    PaymentStatusEnum(Long id){ this.id = id; }
}
