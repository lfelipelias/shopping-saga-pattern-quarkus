package com.lfefox.common.resource;



import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.*;

import java.util.Date;

/**
 * Felipe.Elias
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@RegisterForReflection
public class PaymentResource {

    private Long paymentId;
    private Long orderId;
    private Date createDate;
    private String status;
    private Long  statusId;

}
