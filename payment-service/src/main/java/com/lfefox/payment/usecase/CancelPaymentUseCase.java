package com.lfefox.payment.usecase;

import com.lfefox.common.enums.OrderStatusEnum;
import com.lfefox.common.enums.TransactionEventTypeEnum;
import com.lfefox.common.resource.OrderInfoResource;
import com.lfefox.common.resource.PaymentResource;
import com.lfefox.payment.event.OrderEventProducer;
import com.lfefox.payment.service.PaymentService;
import io.smallrye.mutiny.Uni;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

/**
 * Felipe.Elias
 */
@Slf4j
@ApplicationScoped
@RequiredArgsConstructor
public class CancelPaymentUseCase {

    private final PaymentService paymentService;
    private final OrderEventProducer orderEventProducer;


    public Uni<Void> cancelPayment(PaymentResource paymentResource) {

        log.info("BEGIN COMPENSATION FOR PAYMENT: {}", paymentResource);

        return paymentService
                .compensatePayment(paymentResource)
                .onItem()
                .call(ex -> sendCompensationOrderEvent(paymentResource));
    }

    /**
     * Sends the compensation for order service
     * @param paymentResource
     * @return
     */
    private Uni<Void> sendCompensationOrderEvent(PaymentResource paymentResource){
        final OrderInfoResource orderResource = new OrderInfoResource();
        orderResource.setOrderId(paymentResource.getOrderId());
        orderResource.setStatus(paymentResource.getStatus());
        orderResource.setStatusId(paymentResource.getStatusId());
        orderResource.setTransactionEventType(TransactionEventTypeEnum.COMPENSATION);

        log.info("SENDING COMPENSATE ORDER EVENT: {}", orderResource);
        return orderEventProducer.sendOrderEvent(orderResource);

    }
}
