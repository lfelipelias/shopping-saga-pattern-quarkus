package com.lfefox.order.payment.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lfefox.order.payment.model.Order;
import com.lfefox.order.payment.model.Payment;
import com.lfefox.order.payment.service.PaymentService;
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
public class PaymentEventProducer {

    @Channel("payment-out")
    Emitter<Record<Long, String>> emitter;

    private final OrderEventProducer orderEventProducer;
    private final PaymentService paymentService;

    @SneakyThrows
    public void sendPaymentEvent(Payment payment) {
        log.info("sendPaymentEvent for payment: {}", payment);

        ObjectMapper objectMapper = new ObjectMapper();

        var paymentJson = objectMapper.writeValueAsString(payment);

        emitter.send(Record.of(payment.getPaymentId(), paymentJson))
                .whenComplete((success, failure) -> {
                    if (failure != null) {
                        log.error("Error sending message to product-service on channel {} error: {} ", "payment-out", failure.getMessage());
                        final Order order = new Order();
                        order.setStatus("ERROR_SEND_PAYMENT_EVENT_OUT");
                        order.setId(payment.getOrderId());

                        orderEventProducer.sendOrderEvent(order);
                        paymentService.compensatePayment(payment);

                    } else {

                        log.info("Message for product-service sent successfully on channel: {}", "payment-out");

                    }
                });

    }
}
