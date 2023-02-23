package com.lfefox.order.payment.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * Felipe.Elias
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Payment {

    private Long paymentId;
    private Long orderId;
    private Date timeStamp;
    private String paymentStatus;

}
