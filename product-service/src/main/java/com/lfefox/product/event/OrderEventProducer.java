package com.lfefox.product.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lfefox.common.model.Order;
import io.smallrye.reactive.messaging.kafka.Record;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 * Felipe.Elias
 */
@Slf4j
@ApplicationScoped
@RequiredArgsConstructor
public class OrderEventProducer {

    @Inject
    @Channel("order-out")
    Emitter<Record<Long, String>> emitter;

    @SneakyThrows
    public void sendOrderEvent(Order order) {
        log.info("sendOrderEvent: {}" , order);

        ObjectMapper objectMapper = new ObjectMapper();

        final String jsonToSend = objectMapper.writeValueAsString(order);

        emitter.send(Record.of(order.getOrderId(), jsonToSend))
                .whenComplete((success, failure) -> {
                    if (failure != null) {
                        log.error("Error sending message to payment-service on channel {} error: {} ", "order-out", failure.getMessage());
                    } else {
                        log.info("Message for payment-service sent successfully on channel: {}", "order-out");
                    }
                });
    }
}
