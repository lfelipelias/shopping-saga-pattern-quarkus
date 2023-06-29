package com.lfefox.payment.event.compensation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lfefox.common.resource.PaymentResource;
import com.lfefox.payment.usecase.CancelPaymentUseCase;
import io.smallrye.mutiny.Uni;
import io.smallrye.reactive.messaging.kafka.Record;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.control.ActivateRequestContext;
import javax.transaction.Transactional;

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
    @ActivateRequestContext
    public Uni<Void> receive(Record<Long, String> record) {

        ObjectMapper objectMapper = new ObjectMapper();

        PaymentResource paymentResource = objectMapper.readValue(record.value(), PaymentResource.class);

        log.info("receiving event for payment compensation: {}", paymentResource);
        return cancelPaymentUseCase.cancelPayment(paymentResource);
    }
}
