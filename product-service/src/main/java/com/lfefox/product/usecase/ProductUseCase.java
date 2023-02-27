package com.lfefox.product.usecase;

import com.lfefox.common.enums.OrderStatusEnum;
import com.lfefox.common.enums.PaymentStatusEnum;
import com.lfefox.common.enums.TransactionEventTypeEnum;
import com.lfefox.common.model.Order;
import com.lfefox.common.model.Payment;
import com.lfefox.product.event.OrderEventProducer;
import com.lfefox.product.event.PaymentEventProducer;
import com.lfefox.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import javax.enterprise.context.ApplicationScoped;

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

    public void processProducts(Order order){

        log.info("BEGIN USECASE PROCESS PRODUCTS FOR ORDER: {}", order);

        try{
            productService.processOrder(order);
        } catch(Exception e){

            //SAVING PAYMENT ERROR
            log.info("EXCEPTION DURING PROCESSING PRODUCTS, STARTING PAYMENT COMPENSATION");

            //SENDING COMPENSATION TO ORDER SERVICE
            Payment payment = new Payment();
            payment.setOrderId(order.getOrderId());

            paymentEventProducer.sendPaymentEvent(payment);

            log.info("END USECASE NEW PAYMENT FOR ORDER: {}", order);
            return;
        }



        order.setTransactionEventType(TransactionEventTypeEnum.COMPLETE_ORDER);
        orderEventProducer.sendOrderEvent(order);
        log.info("END USECASE PROCESS PRODUCTS FOR ORDER: {}", order);
    }
}
