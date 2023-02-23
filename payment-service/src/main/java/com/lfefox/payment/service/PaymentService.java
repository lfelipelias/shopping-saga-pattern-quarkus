package com.lfefox.payment.service;

import com.lfefox.common.model.Payment;
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
    public Payment savePayment(Payment payment){
        log.info("savePayment: {}", payment);

        payment.setPaymentId(1L);

        //SAVING PAYMENT IN PAYMENT TABLE

        return payment;
    }

    @Transactional
    public Payment compensatePayment(Payment payment){
        log.info("compensatePayment");
        return payment;
    }
    /**
     *
     * Simulating external call with success payment
     */
    public void makePaymentExternalCall(Payment payment, Boolean fail) throws Exception {

        log.info("makePaymentExternalCall");

        if(fail == Boolean.TRUE){
            throw new Exception("Simulating error payment external call");
        }

    }
}
