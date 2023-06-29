package com.lfefox.payment.usecase;

import com.lfefox.common.enums.OrderStatusEnum;
import com.lfefox.common.enums.PaymentStatusEnum;
import com.lfefox.common.enums.TransactionEventTypeEnum;
import com.lfefox.common.resource.OrderInfoResource;
import com.lfefox.common.resource.PaymentResource;
import com.lfefox.payment.event.OrderEventProducer;
import com.lfefox.payment.event.ProductEventProducer;
import com.lfefox.payment.service.PaymentService;

import io.smallrye.mutiny.Uni;
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


    public Uni<Void> makePayment(OrderInfoResource orderResource){
        log.info("BEGIN USECASE NEW PAYMENT FOR ORDER: {}", orderResource);

        //SAVE NEW PAYMENT
        PaymentResource paymentResource = new PaymentResource();
        paymentResource.setStatus(PaymentStatusEnum.IN_PROGRESS.name());
        paymentResource.setStatusId(PaymentStatusEnum.IN_PROGRESS.getId());
        paymentResource.setOrderId(orderResource.getOrderId());

        return paymentService
                .savePayment(paymentResource)
                    .onItem()
                        .ifNotNull()
                        .call(localItem -> productEventProducer.sendProductEvent(localItem))
                    .onFailure()
                        .recoverWithNull()
                        .call(ex -> sendCompensationOrderEvent(orderResource))
                    .replaceWithVoid();
    }

    /**
     * Sends the compensation for order service
     * @param orderResource
     * @return
     */
    private Uni<Void> sendCompensationOrderEvent(OrderInfoResource orderResource){

            //SENDING COMPENSATION TO ORDER SERVICE
            orderResource.setStatus(OrderStatusEnum.ERROR_PAYMENT.name());
            orderResource.setStatusId(OrderStatusEnum.ERROR_PAYMENT.getId());
            orderResource.setTransactionEventType(TransactionEventTypeEnum.COMPENSATION);
            return orderEventProducer.sendOrderEvent(orderResource);
    }
}
