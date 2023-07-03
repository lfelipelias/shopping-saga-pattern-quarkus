package com.lfefox.product.usecase;

import com.lfefox.common.enums.OrderStatusEnum;
import com.lfefox.common.enums.TransactionEventTypeEnum;
import com.lfefox.common.resource.OrderInfoResource;
import com.lfefox.common.resource.PaymentResource;
import com.lfefox.product.event.OrderEventProducer;
import com.lfefox.product.event.PaymentEventProducer;
import com.lfefox.product.service.ProductService;
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
public class ProductUseCase {

    private final OrderEventProducer orderEventProducer;
    private final PaymentEventProducer paymentEventProducer;

    private final ProductService productService;

    public Uni<Void> processProducts(OrderInfoResource orderResource){
        log.info("BEGIN USECASE NEW PRODUCT FOR ORDER: {}", orderResource);
        return productService
                .markProductsAsSold(orderResource)
                    .onItem()
                        .call(() -> sendOrderCompleteEvent(orderResource))
                    .onFailure()
                        .call(()->{

                            log.info("EXCEPTION DURING PROCESSING PRODUCTS, STARTING PAYMENT COMPENSATION");

                            //SENDING COMPENSATION TO ORDER SERVICE
                            PaymentResource paymentResource = new PaymentResource();
                            paymentResource.setOrderId(orderResource.getOrderId());
                            paymentResource.setStatus(OrderStatusEnum.ERROR_PRODUCT.name());
                            paymentResource.setStatusId(OrderStatusEnum.ERROR_PRODUCT.getId());

                            return productService
                                    .markProductsAsAvailable(orderResource)
                                    .onItem()
                                        .call(() -> paymentEventProducer.sendPaymentEvent(paymentResource));
                        });
    }

    private Uni<Void> sendOrderCompleteEvent(OrderInfoResource orderResource){

        //SENDING COMPENSATION TO ORDER SERVICE
        orderResource.setTransactionEventType(TransactionEventTypeEnum.COMPLETE_ORDER);
        return orderEventProducer.sendOrderEvent(orderResource);
    }
}
