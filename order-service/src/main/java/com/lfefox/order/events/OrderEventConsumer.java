package com.lfefox.order.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lfefox.common.enums.OrderStatusEnum;
import com.lfefox.common.enums.TransactionEventTypeEnum;
import com.lfefox.common.model.Order;
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

        ObjectMapper objectMapper = new ObjectMapper();

        final Order order = objectMapper.readValue(record.value(), Order.class);

        log.info("receiving event for order: {}", order);
        if (TransactionEventTypeEnum.COMPENSATION == order.getTransactionEventType()) {
            order.setStatus(OrderStatusEnum.ERROR_PAYMENT.name());
            order.setStatusId(OrderStatusEnum.ERROR_PAYMENT.getId());
            cancelOrderUseCase.cancelOrder(order);

        } else if (TransactionEventTypeEnum.COMPLETE_ORDER == order.getTransactionEventType()) {
            //SUCESS FINISH ORDER

            order.setStatus(OrderStatusEnum.SUCCESS.name());
            order.setStatusId(OrderStatusEnum.SUCCESS.getId());
            orderService.updateOrder(order);

        }

    }
}
