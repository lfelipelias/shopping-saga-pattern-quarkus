package com.lfefox.order.usecase;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lfefox.common.resource.OrderInfoResource;
import com.lfefox.order.service.OrderService;
import io.smallrye.mutiny.Uni;
import io.smallrye.reactive.messaging.kafka.Record;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@Slf4j
@ApplicationScoped
@RequiredArgsConstructor
public class NewOrderUserCase {

    final OrderService orderService;

    @Inject
    @Channel("product-update-out")
    Emitter<Record<Long, String>> emitter;

    @SneakyThrows
    public Uni<OrderInfoResource> newOrder(OrderInfoResource orderResource){
        log.info("BEGIN USECASE NEW ORDER: {}", orderResource);

        Uni<OrderInfoResource> inserted = orderService
                .createOrder(orderResource)
                .onItem().invoke(localItem -> sendProductEvent(localItem));

        log.info("END USECASE NEW ORDER");

        return inserted;
    }

    /**
     * sendProductEvent
     * @param orderResource
     */
    private void sendProductEvent(OrderInfoResource orderResource){
        ObjectMapper objectMapper = new ObjectMapper();
        final String jsonToSend;
        try {
            jsonToSend = objectMapper.writeValueAsString(orderResource);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        log.info("sendProductEvent SELL_IN_PROGRESS for products: {}", orderResource);

        emitter.send(Record.of(orderResource.getOrderId(), jsonToSend))
            .whenComplete((success, failure) -> {
                if (failure != null) {
                    log.error("Error sending message to product-service on channel {} error: {} ", "payment-out", failure.getMessage());
                } else {
                    log.info("Message for product-service sent successfully on channel: {}", "payment-out");
                }
            });
    }
}
