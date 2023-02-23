package com.lfefox.order.service;

import com.lfefox.order.model.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.UUID;
@Slf4j
@ApplicationScoped
@RequiredArgsConstructor
public class OrderService {

    @Transactional
    public Order saveOrder(Order order){
        log.info("save order in DB", order);
        //TODO PERSIST ORDER DATABASE
        order.setOrderUuid(UUID.randomUUID().toString());
        order.setStatus("IN_PROCESS");
        return order;
    }

    @Transactional
    public Order cancelOrder(Order order){
        log.info("cancel order in DB", order);

        return order;
    }

    @Transactional
    public Order updateOrder(Order order){
        log.info("update order in DB", order);

        return order;
    }
}
