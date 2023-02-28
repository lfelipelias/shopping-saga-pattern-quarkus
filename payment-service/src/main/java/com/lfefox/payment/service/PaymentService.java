package com.lfefox.payment.service;

import com.lfefox.common.enums.PaymentStatusEnum;
import com.lfefox.common.resource.PaymentResource;
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
public class PaymentService {

    @Transactional
    public PaymentResource savePayment(PaymentResource paymentResource){
        log.info("savePayment: {}", paymentResource);

        paymentResource.setPaymentId(1L);

        //SAVING PAYMENT IN PAYMENT TABLE

        return paymentResource;
    }

    @Transactional
    public PaymentResource compensatePayment(PaymentResource paymentResource){
        log.info("compensatePayment returning money and marking payment as compensated");

        //SIMULATE FIND PAYMENT BY ID TO RETRIEVE ORDER ID
        paymentResource.setOrderId(1L);
        //END SIMULATION

        paymentResource.setStatus(PaymentStatusEnum.REFUND_DUE_COMPENSATION.name());
        paymentResource.setStatusId(PaymentStatusEnum.REFUND_DUE_COMPENSATION.getId());

        savePayment(paymentResource);

        return paymentResource;
    }
    /**
     *
     * Simulating external call with success payment
     */
    public void makePaymentExternalCall(PaymentResource paymentResource, Boolean fail) throws Exception {

        log.info("makePaymentExternalCall");

        if(fail == Boolean.TRUE){
            throw new Exception("Simulating error payment external call");
        }

    }
}
