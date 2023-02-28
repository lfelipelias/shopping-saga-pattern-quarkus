package com.lfefox.order.service;


import com.lfefox.common.enums.OrderStatusEnum;
import com.lfefox.common.resource.OrderResource;
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
    public OrderResource saveOrder(OrderResource orderResource){

        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setUserId(2L);
        orderInfo.setStatus(OrderStatusEnum.IN_PROGRESS.name());
        orderInfo.setStatusId(OrderStatusEnum.IN_PROGRESS.getId());

        orderInfo.persist();



        List<OrderInfo> orders = orderInfo.findAll().list();

        log.info("save order in DB: {}", orderResource);
        //TODO PERSIST ORDER DATABASE
        orderResource.setOrderId(1L);
        orderResource.setOrderUuid(UUID.randomUUID().toString());
        orderResource.setStatus(OrderStatusEnum.IN_PROGRESS.name());
        orderResource.setStatusId(OrderStatusEnum.IN_PROGRESS.getId());
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
