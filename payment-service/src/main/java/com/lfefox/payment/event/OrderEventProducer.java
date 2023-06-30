package com.lfefox.payment.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lfefox.common.resource.OrderInfoResource;
import io.smallrye.mutiny.Uni;
import io.smallrye.reactive.messaging.MutinyEmitter;
import io.smallrye.reactive.messaging.kafka.Record;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Channel;
import javax.enterprise.context.ApplicationScoped;

/**
 * Felipe.Elias
 */
@Slf4j
@ApplicationScoped
@RequiredArgsConstructor
public class OrderEventProducer {

    @Channel("order-out")
    MutinyEmitter<Record<Long, String>> emitter;

    @SneakyThrows
    public Uni<Void> sendOrderEvent(OrderInfoResource orderResource) {
        log.info("sendOrderEvent: {}" , orderResource);

        ObjectMapper objectMapper = new ObjectMapper();

        final String jsonToSend = objectMapper.writeValueAsString(orderResource);

        return emitter.send(Record.of(orderResource.getOrderId(), jsonToSend))
                .onItem()
                    .invoke(() -> log.info("Message for order-service sent successfully on channel: {}", "order-out"))
                .onFailure()
                    .invoke(failure ->log.error("Error sending message to order-service on channel {} error: {} ", "order-out", failure.getMessage()));
    }
}