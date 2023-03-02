package com.lfefox.payment.usecase;

import com.lfefox.common.enums.OrderStatusEnum;
import com.lfefox.common.enums.TransactionEventTypeEnum;
import com.lfefox.common.resource.OrderInfoResource;
import com.lfefox.common.resource.PaymentResource;
import com.lfefox.payment.event.OrderEventProducer;
import com.lfefox.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;

/**
 * Felipe.Elias
 */
@Slf4j
@ApplicationScoped
@RequiredArgsConstructor
public class CancelPaymentUseCase {

    private final PaymentService paymentService;
    private final OrderEventProducer orderEventProducer;


    public void cancelPayment(PaymentResource paymentResource) {

        log.info("BEGIN COMPENSATION FOR PAYMENT: {}", paymentResource);

        paymentResource = paymentService.compensatePayment(paymentResource);

        final OrderInfoResource orderResource = new OrderInfoResource();
        orderResource.setOrderId(paymentResource.getOrderId());
        orderResource.setStatus(OrderStatusEnum.ERROR_PAYMENT.name());
        orderResource.setStatusId(OrderStatusEnum.ERROR_PAYMENT.getId());
        orderResource.setTransactionEventType(TransactionEventTypeEnum.COMPENSATION);

        log.info("SENDING COMPENSATE ORDER EVENT: {}", orderResource);
        orderEventProducer.sendOrderEvent(orderResource);

        log.info("END COMPENSATION FOR PAYMENT: {}", paymentResource);
    }
}
