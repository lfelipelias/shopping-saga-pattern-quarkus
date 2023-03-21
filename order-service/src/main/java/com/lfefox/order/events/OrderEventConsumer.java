package com.lfefox.order.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lfefox.common.enums.OrderStatusEnum;
import com.lfefox.common.enums.TransactionEventTypeEnum;
import com.lfefox.common.resource.OrderInfoResource;
import com.lfefox.order.service.OrderService;
import com.lfefox.order.usecase.CancelOrderUseCase;
import io.smallrye.reactive.messaging.kafka.Record;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

/**
 * Felipe.Elias
 */
@Slf4j
@ApplicationScoped

public class OrderEventConsumer {
    @Inject
    private CancelOrderUseCase cancelOrderUseCase;
    @Inject
    private OrderService orderService;

    @Transactional
    @SneakyThrows
    @Incoming("order-in")
    public void receive(Record<Long, String> record) {

        ObjectMapper objectMapper = new ObjectMapper();

        final OrderInfoResource orderResource = objectMapper.readValue(record.value(), OrderInfoResource.class);

        log.info("receiving event for order: {}", orderResource);
        if (TransactionEventTypeEnum.COMPENSATION == orderResource.getTransactionEventType()) {
            orderResource.setStatus(OrderStatusEnum.ERROR_PAYMENT.name());
            orderResource.setStatusId(OrderStatusEnum.ERROR_PAYMENT.getId());
            cancelOrderUseCase.cancelOrder(orderResource);

        } else if (TransactionEventTypeEnum.COMPLETE_ORDER == orderResource.getTransactionEventType()) {
            //SUCESS FINISH ORDER

            orderResource.setStatus(OrderStatusEnum.SUCCESS.name());
            orderResource.setStatusId(OrderStatusEnum.SUCCESS.getId());
            orderService.updateOrder(orderResource);

        }

    }
}
