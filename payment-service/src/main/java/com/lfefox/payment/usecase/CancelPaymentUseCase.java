package com.lfefox.payment.usecase;

import com.lfefox.common.enums.OrderStatusEnum;
import com.lfefox.common.enums.TransactionEventTypeEnum;
import com.lfefox.common.model.Order;
import com.lfefox.common.model.Payment;
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


    public void cancelPayment(Payment payment) {

        log.info("COMPENSATION FOR PAYMENT: {}", payment);

        payment = paymentService.compensatePayment(payment);

        final Order order = new Order();
        order.setOrderId(payment.getOrderId());
        order.setStatus(OrderStatusEnum.ERROR_PAYMENT.name());
        order.setStatusId(OrderStatusEnum.ERROR_PAYMENT.getId());
        order.setTransactionEventType(TransactionEventTypeEnum.COMPENSATION);

        log.info("SENDING COMPENSATE ORDER EVENT: {}", order);
        orderEventProducer.sendOrderEvent(order);

        log.info("END FOR PAYMENT: {}", payment);
    }
}
