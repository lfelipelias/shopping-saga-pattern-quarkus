package com.lfefox.order.converter;

import com.lfefox.common.resource.OrderInfoResource;
import com.lfefox.order.entity.OrderInfo;
import com.lfefox.order.entity.OrderProduct;
import org.apache.commons.lang3.ObjectUtils;
import java.util.*;

/**
 * Felipe.Elias
 */
public class OrderInfoConverter {

    /**
     * Converts a list of entities to list of resources
     * @param entities to be converted
     * @return list of resources
     */
    public static List<OrderInfoResource> toListResource(final Iterable<OrderInfo>  entities){
        final List<OrderInfoResource> list = new ArrayList<OrderInfoResource>();
        for (OrderInfo entity : entities) {
            list.add(toResource(entity));
        }
        return list;
    }

    /**
     * Converts an entity to a resource
     * @param entity to be converted
     * @return resource
     */
    public static OrderInfoResource toResource(final OrderInfo entity) {
        OrderInfoResource resource = null;

        if (entity != null) {

            resource = new OrderInfoResource();
            resource.setOrderId(entity.getOrderId());
            resource.setUserId(entity.getUserId());
            resource.setStatus(entity.getStatus());
            resource.setStatusId(entity.getStatusId());
            resource.setCreateDate(entity.getCreateDate());
            resource.setTotalOrder(entity.getTotalOrder());

            if(!ObjectUtils.isEmpty(entity.getOrderProducts())){
                resource.setOrderProductResources(OrderProductConverter.toListResource(entity.getOrderProducts()));
            }


        }
        return resource;
    }

    /**
     * Converts a List of resources to a Set of entities
     * @param resources
     * @return set of entities
     */
    public static Set<OrderInfo> toIterableEntity(final List<OrderInfoResource> resources) {
        final Set<OrderInfo> set = new HashSet<OrderInfo>();

        for(OrderInfoResource resource: resources){
            set.add(toEntity(resource));
        }
        return set;
    }
    /**
     * Converts a resource to entity
     * @param resource to be converted
     * @return entity
     */
    public static OrderInfo toEntity(OrderInfoResource resource){
        OrderInfo entity =  null;
        if(resource != null){
            entity = new OrderInfo();
            entity.setCreateDate(Calendar.getInstance().getTime());

            entity.setUserId(resource.getUserId());
            entity.setTotalOrder(resource.getTotalOrder());
            entity.setStatus(resource.getStatus());
            entity.setStatusId(resource.getStatusId());


            if(!ObjectUtils.isEmpty(resource.getOrderProductResources())){
                entity.setOrderProducts(OrderProductConverter.toIterableEntity(resource.getOrderProductResources()));
                for (OrderProduct orderProduct : entity.getOrderProducts()) {
                    orderProduct.setOrderInfo(entity);
                }
            }

        }
        return entity;
    }
}
