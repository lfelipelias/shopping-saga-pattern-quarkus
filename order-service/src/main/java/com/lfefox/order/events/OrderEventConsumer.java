package com.lfefox.order.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lfefox.order.model.Order;
import com.lfefox.order.service.OrderService;
import com.lfefox.order.usecase.CancelOrderUseCase;
import io.smallrye.reactive.messaging.kafka.Record;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import javax.enterprise.context.ApplicationScoped;

/**
 * Felipe.Elias
 */
@Slf4j
@ApplicationScoped
@RequiredArgsConstructor
public class OrderEventConsumer {
    private final CancelOrderUseCase cancelOrderUseCase;
    private final OrderService orderService;

    @SneakyThrows
    @Incoming("order-in")
    public void receive(Record<Long, String> record) {

        log.info("record es: {}", record.key());

        ObjectMapper objectMapper = new ObjectMapper();

        final Order order = objectMapper.readValue(record.value(), Order.class);

        if (null != order.getType() && order.getType().equalsIgnoreCase("compensation")) {

            cancelOrderUseCase.cancelOrder(order);

        } else {
            //SUCESS FINISH ORDER
            orderService.updateOrder(order);

        }

    }
}