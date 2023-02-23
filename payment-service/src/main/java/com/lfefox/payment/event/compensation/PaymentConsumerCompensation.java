package com.lfefox.payment.event.compensation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lfefox.common.enums.OrderStatusEnum;
import com.lfefox.common.enums.PaymentStatusEnum;
import com.lfefox.common.enums.TransactionEventTypeEnum;
import com.lfefox.common.model.Order;
import com.lfefox.common.model.Payment;
import com.lfefox.payment.event.OrderEventProducer;
import com.lfefox.payment.service.PaymentService;
import com.lfefox.payment.usecase.CancelPaymentUseCase;
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
public class PaymentConsumerCompensation {

    private final CancelPaymentUseCase cancelPaymentUseCase;


    @SneakyThrows
    @Incoming("product-in")
    public void receive(Record<Long, String> record) {

        ObjectMapper objectMapper = new ObjectMapper();

        Payment payment = objectMapper.readValue(record.value(), Payment.class);

        log.info("receiving event for payment compensation: {}", payment);
        cancelPaymentUseCase.cancelPayment(payment);
    }
}
