package com.lfefox.service;

import com.lfefox.model.Order;
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
        order.setOrderUuid(UUID.randomUUID().toString());
        order.setStatus("IN_PROCESS");
        return order;
    }
}
