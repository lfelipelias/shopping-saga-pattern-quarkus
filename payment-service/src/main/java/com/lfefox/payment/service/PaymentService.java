package com.lfefox.payment.service;

import com.lfefox.common.enums.PaymentStatusEnum;
import com.lfefox.common.resource.PaymentResource;
import com.lfefox.payment.converter.PaymentConverter;
import com.lfefox.payment.entity.Payment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.Calendar;

/**
 * Felipe.Elias
 */
@Slf4j
@ApplicationScoped
@RequiredArgsConstructor
public class PaymentService {

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public PaymentResource savePayment(PaymentResource paymentResource){
        log.info("savePayment: {}", paymentResource);
        Payment payment = PaymentConverter.toEntity(paymentResource);


        payment.persist();
        return PaymentConverter.toResource(payment);
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public PaymentResource updatePayment(PaymentResource paymentResource){
        log.info("updatePayment: {}", paymentResource);
        Payment payment = Payment.findById(paymentResource.getPaymentId());

        payment.setStatus(paymentResource.getStatus());
        payment.setStatusId(paymentResource.getStatusId());

        payment.persist();
        return PaymentConverter.toResource(payment);
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public PaymentResource compensatePayment(PaymentResource paymentResource){
        log.info("compensatePayment returning money and marking payment as compensated");

        Payment payment = Payment.find("orderId", paymentResource.getOrderId()).firstResult();
        payment.setStatus(PaymentStatusEnum.REFUND_DUE_COMPENSATION.name());
        payment.setStatusId(PaymentStatusEnum.REFUND_DUE_COMPENSATION.getId());
        payment.persist();

        return PaymentConverter.toResource(payment);
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
