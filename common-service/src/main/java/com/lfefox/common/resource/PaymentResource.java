package com.lfefox.common.resource;



import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.*;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.io.Serializable;
import java.util.Date;

/**
 * Felipe.Elias
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class PaymentResource implements Serializable {

    private static final long serialVersionUID = 5661774248480187338L;

    @Schema(example= "1", description = "payment ID")
    private Long paymentId;

    @Schema(example= "1", description = "order ID")
    private Long orderId;

    private Date createDate;

    @Schema(example= "IN_PROGRESS", description = "status description")
    private String status;

    @Schema(example= "1", description = "status ID")
    private Long  statusId;

}
