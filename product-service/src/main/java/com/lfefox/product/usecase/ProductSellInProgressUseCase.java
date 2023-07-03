package com.lfefox.product.usecase;

import com.lfefox.common.enums.OrderStatusEnum;
import com.lfefox.common.enums.TransactionEventTypeEnum;
import com.lfefox.common.resource.OrderInfoResource;
import com.lfefox.product.event.OrderEventProducer;
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
public class ProductSellInProgressUseCase {

    private final ProductService productService;
    private final OrderEventProducer orderEventProducer;

    public Uni<Void> updateProductStatus(OrderInfoResource orderResource){
        log.info("BEGIN USECASE PRODUCT SELL IN PROGRESS FOR ORDER: {}", orderResource);

        return productService
                .changeProductsToSellInProgress(orderResource)
                .onItem()
                    .invoke(() -> log.info("changeProductsToSellInProgress() finished successfully"))
                .onFailure()
                    .call(()->{

                        log.info("EXCEPTION DURING SETTING SELL IN PROCESS FOR ORDERS, STARTING ORDER COMPENSATION");
                        orderResource.setStatus(OrderStatusEnum.ERROR_PRODUCT_NOT_AVAILABLE.name());
                        orderResource.setStatusId(OrderStatusEnum.ERROR_PRODUCT_NOT_AVAILABLE.getId());
                        orderResource.setTransactionEventType(TransactionEventTypeEnum.COMPENSATION);

                        return productService
                                .changeProductsToSellInProgress(orderResource)
                                .onItem()
                                    .call(() -> orderEventProducer.sendOrderEvent(orderResource));
                    });
    }
}
