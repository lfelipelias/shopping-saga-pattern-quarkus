package com.lfefox.order.usecase;

import com.lfefox.common.resource.OrderInfoResource;
import com.lfefox.order.service.OrderService;
import io.smallrye.mutiny.Uni;
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
    public Uni<OrderInfoResource> cancelOrder(OrderInfoResource orderResource){
        log.info("BEGIN COMPENSATION FOR ORDER: {}", orderResource);
        return orderService.updateOrder(orderResource);
    }
}
