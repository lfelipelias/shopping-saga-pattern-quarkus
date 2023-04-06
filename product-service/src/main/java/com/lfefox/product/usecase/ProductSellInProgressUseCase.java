package com.lfefox.product.usecase;

import com.lfefox.common.enums.OrderStatusEnum;
import com.lfefox.common.enums.TransactionEventTypeEnum;
import com.lfefox.common.resource.OrderInfoResource;
import com.lfefox.product.event.OrderEventProducer;
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
public class ProductSellInProgressUseCase {

    private final ProductService productService;
    private final OrderEventProducer orderEventProducer;

    @Transactional
    public void updateProductStatus(OrderInfoResource orderResource){
        log.info("BEGIN USECASE PRODUCT SELL IN PROGRESS FOR ORDER: {}", orderResource);

        try{
            productService.changeProductsToSellInProgress(orderResource);
        } catch(Exception e){
            productService.changeProductsToAvailable(orderResource);

            log.info("EXCEPTION DURING SETTING SELL IN PROCESS FOR ORDERS, STARTING ORDER COMPENSATION");
            orderResource.setStatus(OrderStatusEnum.ERROR_PRODUCT_NOT_AVAILABLE.name());
            orderResource.setStatusId(OrderStatusEnum.ERROR_PRODUCT_NOT_AVAILABLE.getId());
            orderResource.setTransactionEventType(TransactionEventTypeEnum.COMPENSATION);
            orderEventProducer.sendOrderEvent(orderResource);
        }


        log.info("END USECASE PRODUCT SELL IN PROGRESS  FOR ORDER: {}", orderResource);
    }
}
