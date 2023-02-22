package com.lfefox.service;

import com.lfefox.model.Order;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.UUID;

@ApplicationScoped
public class OrderService {

    @Transactional
    public Order saveOrder(Order order){
        order.setOrderUuid(UUID.randomUUID().toString());
        order.setStatus("IN_PROCESS");
        return order;
    }
}
