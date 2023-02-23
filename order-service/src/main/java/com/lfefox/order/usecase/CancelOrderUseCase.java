package com.lfefox.order.usecase;

import com.lfefox.order.model.Order;
import com.lfefox.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;

@Slf4j
@ApplicationScoped
@RequiredArgsConstructor
public class CancelOrderUseCase {

    private final OrderService orderService;

    @SneakyThrows
    public void cancelOrder(Order order){
        log.info("BEGINNING USECASE CANCEL ORDER: {}", order);
        orderService.cancelOrder(order);

    }
}
