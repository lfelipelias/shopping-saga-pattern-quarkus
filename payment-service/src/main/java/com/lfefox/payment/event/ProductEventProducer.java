package com.lfefox.payment.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lfefox.common.enums.OrderStatusEnum;
import com.lfefox.common.enums.TransactionEventTypeEnum;
import com.lfefox.common.resource.OrderInfoResource;
import com.lfefox.common.resource.PaymentResource;
import com.lfefox.payment.service.PaymentService;
import io.smallrye.mutiny.Uni;
import io.smallrye.reactive.messaging.MutinyEmitter;
import io.smallrye.reactive.messaging.kafka.Record;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.control.ActivateRequestContext;

/**
 * Felipe.Elias
 */
@Slf4j
@ApplicationScoped
@RequiredArgsConstructor
public class ProductEventProducer {

    @Channel("product-out")
    MutinyEmitter<Record<Long, String>> emitter;

    private final OrderEventProducer orderEventProducer;
    private final PaymentService paymentService;

    @SneakyThrows
    public Uni<Void> sendProductEvent(PaymentResource paymentResource) {
        log.info("sendProductEvent for payment: {}", paymentResource);

        final OrderInfoResource orderResource = new OrderInfoResource();
        orderResource.setOrderId(paymentResource.getOrderId());

        ObjectMapper objectMapper = new ObjectMapper();

        var paymentJson = objectMapper.writeValueAsString(orderResource);

        return emitter.send(Record.of(paymentResource.getPaymentId(), paymentJson))
                .onItem()
                    .invoke(() -> log.info("Message for product-service sent successfully on channel: {}", "payment-out"))
                .onFailure()
                    .call(()->{
                        //FIRST WE COMPENSATE/UPDATE PAYMENT TABLE, THEN WE SEND COMPENSATION MESSAGE TO ORDER-SERVICE

                        log.error("Error sending message to product-service on channel {} ", "payment-out");
                        orderResource.setStatus(OrderStatusEnum.ERROR_PAYMENT.name());
                        orderResource.setStatusId(OrderStatusEnum.ERROR_PAYMENT.getId());
                        orderResource.setTransactionEventType(TransactionEventTypeEnum.COMPENSATION);

                        return paymentService
                                .compensatePayment(paymentResource)
                                    .onItem()
                                        .call(() -> orderEventProducer.sendOrderEvent(orderResource));
                    });
    }
}
