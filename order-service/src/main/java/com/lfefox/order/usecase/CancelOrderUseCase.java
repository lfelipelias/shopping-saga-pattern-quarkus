package com.lfefox.order.usecase;

import com.lfefox.common.resource.OrderResource;
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
    public void cancelOrder(OrderResource orderResource){
        log.info("BEGIN COMPENSATION FOR ORDER: {}", orderResource);
        orderService.cancelOrder(orderResource);
        log.info("END COMPENSATION FOR ORDER: {}", orderResource);

    }
}
