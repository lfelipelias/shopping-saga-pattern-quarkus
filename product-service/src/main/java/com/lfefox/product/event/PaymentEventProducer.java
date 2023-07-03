package com.lfefox.product.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lfefox.common.resource.PaymentResource;
import io.smallrye.mutiny.Uni;
import io.smallrye.reactive.messaging.MutinyEmitter;
import io.smallrye.reactive.messaging.kafka.Record;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Channel;
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
    MutinyEmitter<Record<Long, String>> emitter;


    @SneakyThrows
    public Uni<Void> sendPaymentEvent(PaymentResource paymentResource) {
        log.info("sendPaymentEvent: {}" , paymentResource);

        ObjectMapper objectMapper = new ObjectMapper();

        final String jsonToSend = objectMapper.writeValueAsString(paymentResource);

        return emitter.send(Record.of(paymentResource.getOrderId(), jsonToSend))
                .onItem()
                .invoke(() -> log.info("Message for payment-service sent successfully on channel: {}", "payment-out"))
                .onFailure()
                .invoke(failure -> log.error("Error sending message to payment-service on channel {} error: {} ", "payment-out", failure.getMessage()));
    }
}
