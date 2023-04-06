package com.lfefox.payment.usecase;

import com.lfefox.common.enums.OrderStatusEnum;
import com.lfefox.common.enums.PaymentStatusEnum;
import com.lfefox.common.enums.TransactionEventTypeEnum;
import com.lfefox.common.resource.OrderInfoResource;
import com.lfefox.common.resource.PaymentResource;
import com.lfefox.payment.event.OrderEventProducer;
import com.lfefox.payment.event.ProductEventProducer;
import com.lfefox.payment.service.PaymentService;

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
public class PaymentUseCase {

    private final PaymentService paymentService;
    private final ProductEventProducer productEventProducer;
    private final OrderEventProducer orderEventProducer;


    @Transactional
    public void makePayment(OrderInfoResource orderResource){
        log.info("BEGIN USECASE NEW PAYMENT FOR ORDER: {}", orderResource);

        //SAVE NEW PAYMENT
        PaymentResource paymentResource = new PaymentResource();
        paymentResource.setStatus(PaymentStatusEnum.IN_PROGRESS.name());
        paymentResource.setStatusId(PaymentStatusEnum.IN_PROGRESS.getId());
        paymentResource.setOrderId(orderResource.getOrderId());
        paymentResource = paymentService.savePayment(paymentResource);

        try {
            //SIMULATING EXTERNAL CALL TO PERFORM PAYMENT
            paymentService.makePaymentExternalCall(paymentResource, orderResource.getShouldFail());
        } catch (Exception e) {

            //SAVING PAYMENT ERROR
            log.info("EXCEPTION DURING EXTERNAL PAYMENT, UPDATING PAYMENT AND STARTING ORDER COMPENSATION");
            paymentResource.setStatus(PaymentStatusEnum.ERROR_PROCESSING_PAYMENT.name());
            paymentResource.setStatusId(PaymentStatusEnum.ERROR_PROCESSING_PAYMENT.getId());
            paymentResource = paymentService.updatePayment(paymentResource);

            //SENDING COMPENSATION TO ORDER SERVICE
            orderResource.setStatus(OrderStatusEnum.ERROR_PAYMENT.name());
            orderResource.setStatusId(OrderStatusEnum.ERROR_PAYMENT.getId());
            orderResource.setTransactionEventType(TransactionEventTypeEnum.COMPENSATION);
            orderEventProducer.sendOrderEvent(orderResource);

            log.info("END USECASE NEW PAYMENT FOR ORDER: {}", orderResource);
            return;
        }

        log.info("PAYMENT SUCCESSFUL");
        //SAVE PAYMENT PAID
        paymentResource.setStatus(PaymentStatusEnum.PAID.name());
        paymentResource.setStatusId(PaymentStatusEnum.PAID.getId());

        paymentResource = paymentService.updatePayment(paymentResource);

        productEventProducer.sendProductEvent(paymentResource);

        log.info("END USECASE NEW PAYMENT FOR ORDER: {}", orderResource);
    }
}
