package com.lfefox.order.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Felipe.Elias
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "order_info")
public class OrderInfo  extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    private Long userId;
    private String status;
    private Long  statusId;
}
