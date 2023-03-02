package com.lfefox.order.converter;

import com.lfefox.common.resource.OrderInfoResource;
import com.lfefox.common.resource.OrderProductResource;
import com.lfefox.order.entity.OrderInfo;
import com.lfefox.order.entity.OrderProduct;

import java.util.*;

/**
 * Felipe.Elias
 */
public class OrderProductConverter {

    /**
     * Converts a list of entities to list of resources
     * @param entities to be converted
     * @return list of resources
     */
    public static List<OrderProductResource> toListResource(final Iterable<OrderProduct>  entities){
        final List<OrderProductResource> list = new ArrayList<OrderProductResource>();
        for (OrderProduct entity : entities) {
            list.add(toResource(entity));
        }
        return list;
    }

    /**
     * Converts an entity to a resource
     * @param entity to be converted
     * @return resource
     */
    public static OrderProductResource toResource(final OrderProduct entity) {
        OrderProductResource resource = null;

        if (entity != null) {
            resource = new OrderProductResource();
            resource.setOrderProductId(entity.getOrderProductId());
            resource.setCreateDate(entity.getCreateDate());
            resource.setPrice(entity.getPrice());
            resource.setProductId(entity.getProductId());
        }
        return resource;
    }

    /**
     * Converts a List of resources to a Set of entities
     * @param resources
     * @return set of entities
     */
    public static Set<OrderProduct> toIterableEntity(final List<OrderProductResource> resources) {
        final Set<OrderProduct> set = new HashSet<OrderProduct>();

        for(OrderProductResource resource: resources){
            set.add(toEntity(resource));
        }
        return set;
    }

    /**
     * Converts a resource to entity
     * @param resource to be converted
     * @return entity
     */
    public static OrderProduct toEntity(OrderProductResource resource){
        OrderProduct entity =  null;
        if(resource != null){
            entity = new OrderProduct();
            entity.setCreateDate(Calendar.getInstance().getTime());
            entity.setOrderProductId(entity.getProductId());
            entity.setPrice(entity.getPrice());
            entity.setProductId(entity.getProductId());
        }
        return entity;
    }
}
