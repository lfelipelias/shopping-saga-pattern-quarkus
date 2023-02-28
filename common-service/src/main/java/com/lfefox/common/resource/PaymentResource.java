package com.lfefox.common.resource;



import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Felipe.Elias
 */
@Data
@NoArgsConstructor
public class PaymentResource {

    private Long paymentId;
    private Long orderId;
    private Date createDate;
    private String status;
    private Long  statusId;

}
