package com.lfefox.payment.usecase;

import com.lfefox.common.enums.OrderStatusEnum;
import com.lfefox.common.enums.PaymentStatusEnum;
import com.lfefox.common.enums.TransactionEventTypeEnum;
import com.lfefox.common.model.Order;
import com.lfefox.common.model.Payment;
import com.lfefox.payment.event.OrderEventProducer;
import com.lfefox.payment.event.ProductEventProducer;
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
public class PaymentUseCase {

    private final PaymentService paymentService;
    private final ProductEventProducer productEventProducer;
    private final OrderEventProducer orderEventProducer;


    public Payment makePayment(Order order){
        log.info("BEGIN USECASE NEW PAYMENT FOR ORDER: {}", order);

        //SAVE NEW PAYMENT
        Payment payment = new Payment();
        payment.setStatus(PaymentStatusEnum.IN_PROGRESS.name());
        payment.setStatusId(PaymentStatusEnum.IN_PROGRESS.getId());
        payment.setOrderId(order.getOrderId());
        payment = paymentService.savePayment(payment);

        try {
            //SIMULATING EXTERNAL CALL TO PERFORM PAYMENT
            paymentService.makePaymentExternalCall(payment, order.getShouldFail());
        } catch (Exception e) {

            //SAVING PAYMENT ERROR
            log.info("EXCEPTION DURING EXTERNAL PAYMENT, UPDATING PAYMENT AND STARTING ORDER COMPENSATION");
            payment.setStatus(PaymentStatusEnum.ERROR_PROCESSING_PAYMENT.name());
            payment.setStatusId(PaymentStatusEnum.ERROR_PROCESSING_PAYMENT.getId());
            payment = paymentService.savePayment(payment);

            //SENDING COMPENSATION TO ORDER SERVICE
            order.setTransactionEventType(TransactionEventTypeEnum.COMPENSATION);
            order.setStatus(OrderStatusEnum.ERROR_PAYMENT.name());
            order.setStatusId(OrderStatusEnum.ERROR_PAYMENT.getId());
            orderEventProducer.sendOrderEvent(order);

            log.info("END USECASE NEW PAYMENT FOR ORDER: {}", order);
            return payment;
        }

        log.info("PAYMENT SUCCESSFUL");
        //SAVE PAYMENT PAID
        payment.setStatus(PaymentStatusEnum.PAID.name());
        payment.setStatusId(PaymentStatusEnum.PAID.getId());

        payment = paymentService.savePayment(payment);

        productEventProducer.sendProductEvent(payment);

        log.info("END USECASE NEW PAYMENT FOR ORDER: {}", order);
        return payment;
    }
}
