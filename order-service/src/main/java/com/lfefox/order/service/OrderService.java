package com.lfefox.order.service;


import com.lfefox.common.enums.OrderStatusEnum;
import com.lfefox.common.resource.OrderInfoResource;
import com.lfefox.order.converter.OrderInfoConverter;
import com.lfefox.order.entity.OrderInfo;
import com.lfefox.order.entity.OrderProduct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;

/**
 * Felipe.Elias
 */
@Slf4j
@ApplicationScoped
@RequiredArgsConstructor
public class OrderService {

    @Transactional
    public OrderInfoResource processOrder(OrderInfoResource orderResource){
        log.info("processing order : {}", orderResource);

        OrderInfo orderInfo = OrderInfo.findById(orderResource.getOrderId());
        orderInfo.setStatus(OrderStatusEnum.IN_PROGRESS.name());
        orderInfo.setStatusId(OrderStatusEnum.IN_PROGRESS.getId());
        orderInfo.persist();

        return OrderInfoConverter.toResource(orderInfo);
    }

    @Transactional
    public OrderInfoResource createOrder(OrderInfoResource orderResource){
        log.info("createOrderd in DB: {}", orderResource);

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

        orderInfo.persist();


        return OrderInfoConverter.toResource(orderInfo);
    }

    @Transactional()
    public OrderInfoResource updateOrder(OrderInfoResource orderResource){
        log.info("updateOrder in DB: {}", orderResource);
        OrderInfo orderInfo = OrderInfo.findById(orderResource.getOrderId());
        orderInfo.setStatus(orderResource.getStatus());
        orderInfo.setStatusId(orderResource.getStatusId());
        orderInfo.getCreateDate();
        orderInfo.persist();
        return OrderInfoConverter.toResource(orderInfo);
    }
}
