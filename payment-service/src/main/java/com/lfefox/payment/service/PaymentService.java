package com.lfefox.payment.service;

import com.lfefox.common.enums.PaymentStatusEnum;
import com.lfefox.common.resource.PaymentResource;
import com.lfefox.payment.converter.PaymentConverter;
import com.lfefox.payment.entity.Payment;
import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import io.smallrye.mutiny.Uni;
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

    /**
     * Method responsible for saving payment in DB and simulate external call
     * @param paymentResource
     * @return
     */
    public Uni<PaymentResource> savePayment(PaymentResource paymentResource){
        log.info("savePayment: {}", paymentResource);

        //we call .chain() so we can fetch the entity saved withTransaction(), without the chain() the update fails
        return Panache
                .withTransaction(PaymentConverter.toEntity(paymentResource)::persistAndFlush)
                .chain(savedEntity ->
                        makePaymentExternalCall((Payment) savedEntity, Boolean.FALSE)
                            .onItem()
                                .transform(item-> PaymentConverter.toResource(item))
                );
    }

    /**
     *
     * Simulating external call with success payment
     */
    public Uni<Payment> makePaymentExternalCall(Payment payment, Boolean fail)  {

        log.info("makePaymentExternalCall");

        if(fail == Boolean.TRUE){
            //Updates status to ERROR_PROCESSING_PAYMENT then trow exception
            payment.setStatus(PaymentStatusEnum.ERROR_PROCESSING_PAYMENT.name());
            payment.setStatusId(PaymentStatusEnum.ERROR_PROCESSING_PAYMENT.getId());
            return payment
                    .persistAndFlush()
                    .replaceWith(
                            Uni.createFrom().failure(new Exception("Simulating error payment external call"))
                    );
        } else {
            //Just UPDATE status to PAID
            payment.setStatus(PaymentStatusEnum.PAID.name());
            payment.setStatusId(PaymentStatusEnum.PAID.getId());
        }
        return payment.persistAndFlush();
    }

    public Uni<Void> compensatePayment(PaymentResource paymentResource){
        log.info("compensatePayment returning money and marking payment as compensated");

        return Panache
                .withTransaction(() ->
                    Payment.<Payment> find("orderId", paymentResource.getOrderId()).firstResult()
                        .onItem().ifNotNull().invoke(entity -> {
                            entity.setStatus(PaymentStatusEnum.REFUND_DUE_COMPENSATION.name());
                            entity.setStatusId(PaymentStatusEnum.REFUND_DUE_COMPENSATION.getId());
                        })
                )
                .replaceWithVoid();
    }

}
