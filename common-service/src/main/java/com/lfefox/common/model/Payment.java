package com.lfefox.common.model;



import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Felipe.Elias
 */
@Data
@NoArgsConstructor
public class Payment {

    private Long paymentId;
    private Long orderId;
    private Date timeStamp;
    private String status;
    private Long  statusId;

}
