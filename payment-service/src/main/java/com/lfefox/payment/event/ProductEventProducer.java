package com.lfefox.payment.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lfefox.common.enums.OrderStatusEnum;
import com.lfefox.common.enums.TransactionEventTypeEnum;
import com.lfefox.common.resource.OrderInfoResource;
import com.lfefox.common.resource.PaymentResource;
import com.lfefox.payment.service.PaymentService;
import io.smallrye.mutiny.Uni;
import io.smallrye.reactive.messaging.kafka.Record;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import javax.enterprise.context.ApplicationScoped;

/**
 * Felipe.Elias
 */
@Slf4j
@ApplicationScoped
@RequiredArgsConstructor
public class ProductEventProducer {

    @Channel("product-out")
    Emitter<Record<Long, String>> emitter;

    private final OrderEventProducer orderEventProducer;
    private final PaymentService paymentService;

    @SneakyThrows
    public Uni<Void> sendProductEvent(PaymentResource paymentResource) {
        log.info("sendPaymentEvent for payment: {}", paymentResource);

        final OrderInfoResource orderResource = new OrderInfoResource();
        orderResource.setOrderId(paymentResource.getOrderId());

        ObjectMapper objectMapper = new ObjectMapper();

        var paymentJson = objectMapper.writeValueAsString(orderResource);

        emitter.send(Record.of(paymentResource.getPaymentId(), paymentJson))
                .whenComplete((success, failure) -> {
                    if (failure != null) {
                        log.error("Error sending message to product-service on channel {} error: {} ", "payment-out", failure.getMessage());


                        orderEventProducer
                                .sendOrderEvent(orderResource)
                                .onItem()
                                .call(i -> paymentService.compensatePayment(paymentResource))
                                .replaceWithVoid();

                    } else {

                        log.info("Message for product-service sent successfully on channel: {}", "payment-out");

                    }
                });

        return Uni.createFrom().voidItem();
    }
}
