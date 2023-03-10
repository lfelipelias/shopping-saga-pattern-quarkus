package com.lfefox.payment.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import java.util.Date;

/**
 * Felipe.Elias
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "payment")
public class Payment extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pay_id")
    private Long paymentId;
    @Column(name = "order_id")
    private Long orderId;
    @Column(name = "create_date")
    private Date createDate;
    @Column(name = "status")
    private String status;
    @Column(name = "status_id")
    private Long  statusId;
}
