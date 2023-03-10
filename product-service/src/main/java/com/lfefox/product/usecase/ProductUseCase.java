package com.lfefox.product.usecase;

import com.lfefox.common.enums.TransactionEventTypeEnum;
import com.lfefox.common.resource.OrderInfoResource;
import com.lfefox.common.resource.PaymentResource;
import com.lfefox.product.event.OrderEventProducer;
import com.lfefox.product.event.PaymentEventProducer;
import com.lfefox.product.service.ProductService;
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
public class ProductUseCase {

    private final OrderEventProducer orderEventProducer;
    private final PaymentEventProducer paymentEventProducer;

    private final ProductService productService;

    @Transactional
    public void processProducts(OrderInfoResource orderResource){

        log.info("BEGIN USECASE PROCESS PRODUCTS FOR ORDER: {}", orderResource);

        try{
            productService.processOrder(orderResource);
        } catch(Exception e){

            //SAVING PAYMENT ERROR
            log.info("EXCEPTION DURING PROCESSING PRODUCTS, STARTING PAYMENT COMPENSATION");

            //SENDING COMPENSATION TO ORDER SERVICE
            PaymentResource paymentResource = new PaymentResource();
            paymentResource.setOrderId(orderResource.getOrderId());

            paymentEventProducer.sendPaymentEvent(paymentResource);

            log.info("END USECASE NEW PAYMENT FOR ORDER: {}", orderResource);
            return;
        }



        orderResource.setTransactionEventType(TransactionEventTypeEnum.COMPLETE_ORDER);
        orderEventProducer.sendOrderEvent(orderResource);
        log.info("END USECASE PROCESS PRODUCTS FOR ORDER: {}", orderResource);
    }
}
