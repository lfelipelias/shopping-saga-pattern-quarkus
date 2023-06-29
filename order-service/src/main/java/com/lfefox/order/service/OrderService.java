package com.lfefox.order.service;


import com.lfefox.common.enums.OrderStatusEnum;
import com.lfefox.common.resource.OrderInfoResource;
import com.lfefox.order.converter.OrderInfoConverter;
import com.lfefox.order.entity.OrderInfo;
import com.lfefox.order.entity.OrderProduct;
import io.quarkus.hibernate.reactive.panache.Panache;
import io.smallrye.mutiny.Uni;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import javax.enterprise.context.ApplicationScoped;
import java.util.Calendar;
import java.util.HashSet;

/**
 * Felipe.Elias
 */
@Slf4j
@ApplicationScoped
@RequiredArgsConstructor
public class OrderService {

    public Uni<OrderInfoResource> processOrder(OrderInfoResource orderResource){
        log.info("processing order : {}", orderResource);
        return Panache
                .withTransaction(() -> OrderInfo.<OrderInfo> findById(orderResource.getOrderId())
                    .onItem().ifNotNull().invoke(entity -> {
                        entity.setStatus(OrderStatusEnum.IN_PROGRESS.name());
                        entity.setStatusId(OrderStatusEnum.IN_PROGRESS.getId());
                    })
                )
                .onItem().ifNotNull().transform(updated -> OrderInfoConverter.toSimpleResource(updated));
    }


    public Uni<OrderInfoResource> createOrder(OrderInfoResource orderResource){
        log.info("createOrder in DB: {}", orderResource);

        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setUserId(orderResource.getUserId());
        orderInfo.setStatus(OrderStatusEnum.CREATED.name());
        orderInfo.setStatusId(OrderStatusEnum.CREATED.getId());
        orderInfo.setCreateDate(Calendar.getInstance().getTime());
        orderInfo.setTotalOrder(orderResource.getTotalOrder());
        orderInfo.setOrderProducts(new HashSet<>());

        if(!ObjectUtils.isEmpty(orderResource.getOrderProductResources())){


            orderResource.getOrderProductResources().forEach(op -> {

                OrderProduct orderProduct = new OrderProduct();
                orderProduct.setOrderInfo(orderInfo);
                orderProduct.setProductId(op.getProductId());
                orderProduct.setCreateDate(Calendar.getInstance().getTime());
                orderProduct.setPrice(op.getPrice());

                orderInfo.getOrderProducts().add(orderProduct);
            });
        }
        return Panache
                .withTransaction(orderInfo::persistAndFlush)
                .onItem()
                .transform(inserted -> OrderInfoConverter.toResource((OrderInfo) inserted));
    }

    public Uni<OrderInfoResource> updateOrder(OrderInfoResource orderResource){
        log.info("updateOrder in DB: {}", orderResource);
        return Panache
                .withTransaction(() -> OrderInfo.<OrderInfo> findById(orderResource.getOrderId())
                    .onItem().ifNotNull().invoke(entity -> {
                        entity.setStatus(orderResource.getStatus());
                        entity.setStatusId(orderResource.getStatusId());
                    }))
                    .onItem().ifNotNull().transform(updated -> OrderInfoConverter.toSimpleResource(updated));
    }
}
