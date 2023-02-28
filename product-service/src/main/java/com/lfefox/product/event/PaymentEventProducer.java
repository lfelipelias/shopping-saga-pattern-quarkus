package com.lfefox.product.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lfefox.common.resource.PaymentResource;
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
public class PaymentEventProducer {

    @Inject
    @Channel("payment-out")
    Emitter<Record<Long, String>> emitter;

    @SneakyThrows
    public void sendPaymentEvent(PaymentResource paymentResource) {
        log.info("sendPaymentEvent: {}" , paymentResource);

        ObjectMapper objectMapper = new ObjectMapper();

        final String jsonToSend = objectMapper.writeValueAsString(paymentResource);

        emitter.send(Record.of(paymentResource.getOrderId(), jsonToSend))
                .whenComplete((success, failure) -> {
                    if (failure != null) {
                        log.error("Error sending message to payment-service on channel {} error: {} ", "payment-out", failure.getMessage());
                    } else {
                        log.info("Message for payment-service sent successfully on channel: {}", "payment-out");
                    }
                });
    }
}
