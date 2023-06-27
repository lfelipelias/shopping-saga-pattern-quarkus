package com.lfefox.order.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lfefox.common.enums.OrderStatusEnum;
import com.lfefox.common.enums.TransactionEventTypeEnum;
import com.lfefox.common.resource.OrderInfoResource;
import com.lfefox.order.service.OrderService;
import com.lfefox.order.usecase.CancelOrderUseCase;
import io.smallrye.mutiny.Uni;
import io.smallrye.reactive.messaging.kafka.Record;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.control.ActivateRequestContext;
import javax.inject.Inject;

/**
 * Felipe.Elias
 */
@Slf4j
@ApplicationScoped

public class OrderEventConsumer {
    @Inject
    CancelOrderUseCase cancelOrderUseCase;
    @Inject
    OrderService orderService;

    @SneakyThrows
    @Incoming("order-in")
    @ActivateRequestContext
    public Uni<Void> receive(Record<Long, String> record) {

        ObjectMapper objectMapper = new ObjectMapper();

        final OrderInfoResource orderResource = objectMapper.readValue(record.value(), OrderInfoResource.class);

        log.info("receiving event for order: {}", orderResource);
        if (TransactionEventTypeEnum.COMPENSATION == orderResource.getTransactionEventType()) {
            orderResource.setStatus(orderResource.getStatus());
            orderResource.setStatusId(orderResource.getStatusId());
            return cancelOrderUseCase.cancelOrder(orderResource).replaceWithVoid();

        } else if (TransactionEventTypeEnum.COMPLETE_ORDER == orderResource.getTransactionEventType()) {
            //SUCESS FINISH ORDER
            orderResource.setStatus(OrderStatusEnum.SUCCESS.name());
            orderResource.setStatusId(OrderStatusEnum.SUCCESS.getId());

            return orderService.updateOrder(orderResource).replaceWithVoid();

        }
        return Uni.createFrom().voidItem();
    }
}
