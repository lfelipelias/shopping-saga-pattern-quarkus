package com.lfefox.payment.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lfefox.common.model.Order;
import com.lfefox.payment.usecase.PaymentUseCase;
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
public class PaymentEventConsumer {

    private final PaymentUseCase paymentUseCase;

    @SneakyThrows
    @Incoming("payment-in")
    public void receive(Record<Long, String> record) {



        ObjectMapper objectMapper = new ObjectMapper();

        final Order order = objectMapper.readValue(record.value(), Order.class);

        log.info("receiving event of new order");

        paymentUseCase.makePayment(order);

    }
}
