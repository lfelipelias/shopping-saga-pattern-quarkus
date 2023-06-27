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
public class ProcessOrderUseCase {


    final OrderService orderService;

    @Inject
    @Channel("payment-out")
    Emitter<Record<Long, String>> emitter;


    @SneakyThrows
    public Uni<OrderInfoResource> processOrder(OrderInfoResource orderResource){
        log.info("BEGIN USECASE PROCESS ORDER: {}", orderResource);

        Uni<OrderInfoResource> result = orderService
                .processOrder(orderResource)
                .onItem().ifNotNull().invoke(localItem -> sendPaymentEvent(localItem));

        log.info("END USECASE PROCESS ORDER");

        return result;
    }

    private void sendPaymentEvent(OrderInfoResource orderResource){

        ObjectMapper objectMapper = new ObjectMapper();
        final String jsonToSend;
        try {
            jsonToSend = objectMapper.writeValueAsString(orderResource);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        log.info("sendPaymentEvent for order: {}", orderResource);

        emitter.send(Record.of(orderResource.getOrderId(), jsonToSend))
            .whenComplete((success, failure) -> {
                if (failure != null) {
                    log.error("Error sending message to payment-service on channel {} error: {} ", "payment-out", failure.getMessage());
                } else {
                    log.info("Message for payment-service sent successfully on channel: {}", "payment-out");
                }
            });
    }
}
