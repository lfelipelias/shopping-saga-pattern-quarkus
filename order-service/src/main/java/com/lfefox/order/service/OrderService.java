package com.lfefox.order.service;


import com.lfefox.common.enums.OrderStatusEnum;
import com.lfefox.common.model.Order;
import com.lfefox.order.entity.OrderInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

/**
 * Felipe.Elias
 */
@Slf4j
@ApplicationScoped
@RequiredArgsConstructor
public class OrderService {

    @Transactional
    public Order saveOrder(Order order){

        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setUserId(2L);
        orderInfo.setStatus(OrderStatusEnum.IN_PROGRESS.name());
        orderInfo.setStatusId(OrderStatusEnum.IN_PROGRESS.getId());

        orderInfo.persist();



        List<OrderInfo> orders = orderInfo.findAll().list();

        log.info("save order in DB: {}", order);
        //TODO PERSIST ORDER DATABASE
        order.setOrderId(1L);
        order.setOrderUuid(UUID.randomUUID().toString());
        order.setStatus(OrderStatusEnum.IN_PROGRESS.name());
        order.setStatusId(OrderStatusEnum.IN_PROGRESS.getId());
        return order;
    }

    @Transactional
    public Order cancelOrder(Order order){
        log.info("cancelOrder in DB: {}", order);

        return order;
    }

    @Transactional
    public Order updateOrder(Order order){
        log.info("updateOrder in DB: {}", order);

        return order;
    }
}
