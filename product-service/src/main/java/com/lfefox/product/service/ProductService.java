package com.lfefox.product.service;


import com.lfefox.common.enums.ProductStatusEnum;
import com.lfefox.common.resource.OrderInfoResource;
import com.lfefox.product.entity.Product;
import io.quarkus.hibernate.reactive.panache.Panache;
import io.smallrye.mutiny.Uni;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Felipe.Elias
 */
@Slf4j
@ApplicationScoped
@RequiredArgsConstructor
public class ProductService {

    public Uni<Void> markProductsAsSold(OrderInfoResource orderResource){
        log.info("markProductsAsSold: {}", orderResource);
        return Panache
            .withTransaction(() ->
                Product.<Product> find("orderId", orderResource.getOrderId())
                    .list()
                    .onItem()
                    .ifNotNull()
                        .invoke(entities -> {
                            log.info(String.format("Updating status of products of order %s to %s", orderResource.getOrderId(),ProductStatusEnum.SOLD.name()));
                            if(!ObjectUtils.isEmpty(entities)){
                                entities.stream().forEach(entity ->{
                                    entity.setStatusId(ProductStatusEnum.SOLD.getId());
                                    entity.setStatus(ProductStatusEnum.SOLD.name());
                                });
                            }
                        })
            ).replaceWithVoid();

    }

    public Uni<Void> changeProductsToSellInProgress(OrderInfoResource orderResource){
        log.info("changeProductsToSellInProgress: {}", orderResource);
        List<Long> ids = orderResource.getOrderProductResources().stream().map(obj -> obj.getProductId()).collect(Collectors.toList());

        return Panache
                .withTransaction(() ->
                        Product
                            .list("prodId in ?1", ids)
                                .onItem()
                                .ifNotNull()
                                .invoke(entities -> {
                                    log.info(String.format("Updating status of products of order ID %s to %s", orderResource.getOrderId(),ProductStatusEnum.SOLD.name()));
                                    if(!ObjectUtils.isEmpty(entities)){
                                        entities.stream().forEach(entity -> {
                                            ((Product)entity).setOrderId(orderResource.getOrderId());
                                            ((Product)entity).setStatus(ProductStatusEnum.SELL_IN_PROGRESS.name());
                                            ((Product)entity).setStatusId(ProductStatusEnum.SELL_IN_PROGRESS.getId());
                                        });
                                    }
                                })
                ).replaceWithVoid();
    }

    public Uni<Void> markProductsAsAvailable(OrderInfoResource orderResource){
        log.info("markProductsAsAvailable: {}", orderResource);

        return Panache
            .withTransaction(() ->
                Product.<Product> find("orderId", orderResource.getOrderId())
                    .list()
                    .onItem()
                    .ifNotNull()
                        .invoke(entities -> {
                            log.info(String.format("Updating status of products of order %s to %s", orderResource.getOrderId(),ProductStatusEnum.AVAILABLE.name()));
                            if(!ObjectUtils.isEmpty(entities)){
                                entities.stream().forEach(entity ->{
                                    entity.setStatusId(ProductStatusEnum.AVAILABLE.getId());
                                    entity.setStatus(ProductStatusEnum.AVAILABLE.name());
                                });
                            }
                        })
            ).replaceWithVoid();

    }
}
