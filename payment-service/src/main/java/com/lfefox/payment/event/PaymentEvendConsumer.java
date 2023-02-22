package com.lfefox.payment.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lfefox.payment.model.Order;
import com.lfefox.payment.service.PaymentService;
import io.smallrye.reactive.messaging.kafka.Record;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import javax.enterprise.context.ApplicationScoped;

/**
 * Felipe.Elias
 */
@Slf4j
@ApplicationScoped
@RequiredArgsConstructor
public class PaymentEvendConsumer {

    private final PaymentService paymentService;

    @SneakyThrows
    @Incoming("payments-in")
    public void receive(Record<Long, String> record) {

        log.info("record es: {}", record.key());

        ObjectMapper objectMapper = new ObjectMapper();

        var order = objectMapper.readValue(record.value(), Order.class);

        log.info("order: {} ", order);

        if (null != order.getType() && order.getType().equalsIgnoreCase("compensation")) {

            paymentService.compensatePayment(order);

        } else {

            paymentService.savePayment(order);
        }

    }
}
