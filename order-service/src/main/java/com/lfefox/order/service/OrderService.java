package com.lfefox.order.service;


import com.lfefox.common.enums.OrderStatusEnum;
import com.lfefox.common.resource.OrderResource;
import com.lfefox.order.entity.OrderInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

/**
 * Felipe.Elias
 */
@Slf4j
@ApplicationScoped
@RequiredArgsConstructor
public class OrderService {

    @Transactional
    public OrderResource processOrder(OrderResource orderResource){
        log.info("processing order : {}", orderResource);

        OrderInfo orderInfo = OrderInfo.findById(orderResource.getOrderId());
        orderInfo.setStatus(OrderStatusEnum.IN_PROGRESS.name());
        orderInfo.setStatusId(OrderStatusEnum.IN_PROGRESS.getId());

        orderInfo.persist();

        return orderResource;
    }

    @Transactional
    public OrderResource cancelOrder(OrderResource orderResource){
        log.info("cancelOrder in DB: {}", orderResource);

        return orderResource;
    }

    @Transactional
    public OrderResource updateOrder(OrderResource orderResource){
        log.info("updateOrder in DB: {}", orderResource);

        return orderResource;
    }
}
