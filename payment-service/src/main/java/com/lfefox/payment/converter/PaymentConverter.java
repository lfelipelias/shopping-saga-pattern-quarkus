package com.lfefox.payment.converter;

import com.lfefox.common.resource.PaymentResource;
import com.lfefox.payment.entity.Payment;
import java.util.*;

/**
 * Felipe.Elias
 */
public class PaymentConverter {

    /**
     * Converts a list of entities to list of resources
     * @param entities to be converted
     * @return list of resources
     */
    public static List<PaymentResource> toListResource(final Iterable<Payment>  entities){
        final List<PaymentResource> list = new ArrayList<PaymentResource>();
        for (Payment entity : entities) {
            list.add(toResource(entity));
        }
        return list;
    }

    /**
     * Converts an entity to a resource
     * @param entity to be converted
     * @return resource
     */
    public static PaymentResource toResource(final Payment entity) {
        PaymentResource resource = null;

        if (entity != null) {

            resource = new PaymentResource();
            resource.setPaymentId(entity.getPaymentId());
            resource.setOrderId(entity.getOrderId());
            resource.setStatus(entity.getStatus());
            resource.setStatusId(entity.getStatusId());
            resource.setCreateDate(entity.getCreateDate());

        }
        return resource;
    }

    /**
     * Converts a List of resources to a Set of entities
     * @param resources
     * @return set of entities
     */
    public static Set<Payment> toIterableEntity(final List<PaymentResource> resources) {
        final Set<Payment> set = new HashSet<Payment>();

        for(PaymentResource resource: resources){
            set.add(toEntity(resource));
        }
        return set;
    }
    /**
     * Converts a resource to entity
     * @param resource to be converted
     * @return entity
     */
    public static Payment toEntity(PaymentResource resource){
        Payment entity =  null;
        if(resource != null){
            entity = new Payment();
            entity.setCreateDate(Calendar.getInstance().getTime());
            entity.setOrderId(resource.getOrderId());
            entity.setStatus(resource.getStatus());
            entity.setStatusId(resource.getStatusId());

        }
        return entity;
    }
}
