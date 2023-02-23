package com.lfefox.order.payment.usecase;

import com.lfefox.order.payment.event.OrderEventProducer;
import com.lfefox.order.payment.event.PaymentEventProducer;
import com.lfefox.order.payment.model.Order;
import com.lfefox.order.payment.model.Payment;
import com.lfefox.order.payment.service.PaymentService;
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
    private final PaymentEventProducer paymentEventProducer;
    private final OrderEventProducer orderEventProducer;


    public Payment makePayment(Order order){
        log.info("BEGIN USECASE NEW PAYMENT FOR ORDER: {}", order);

        //PAYMENT STATUS IN_PROGRESS
        Payment payment = new Payment();
        payment.setPaymentStatus("IN_PROGRESS");
        payment.setOrderId(order.getId());
        payment = paymentService.savePayment(payment);

        try {
            //SIMULATING EXTERNAL CALL FOR PAYMENT SERVICE
            paymentService.makePaymentExternalCall(payment, Boolean.TRUE);
        } catch (Exception e) {

            //CASE EXTERNAL PAYMENT FAIL
            order.setType("compensation");
            order.setStatus("ERROR_PAYMENT_EXTERNAL_CALL");
            orderEventProducer.sendOrderEvent(order);
            return payment;
        }


        paymentEventProducer.sendPaymentEvent(payment);

        return payment;

    }
}
