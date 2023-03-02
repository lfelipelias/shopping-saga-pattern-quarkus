package com.lfefox.order.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

/**
 * Felipe.Elias
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "order_info")
public class OrderInfo  extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "order_id")
    private Long orderId;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "create_date")
    private Date createDate;
    @Column(name = "total_order")
    private BigDecimal totalOrder;
    @Column(name = "status")
    private String status;
    @Column(name = "status_id")
    private Long  statusId;

    @OneToMany(mappedBy = "orderInfo", cascade = CascadeType.ALL)
    private Set<OrderProduct> orderProducts;
}
