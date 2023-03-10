package com.lfefox.product.converter;

import com.lfefox.common.resource.ProductResource;
import com.lfefox.product.entity.Product;

import java.util.*;

/**
 * Felipe.Elias
 */
public class ProductConverter {

    /**
     * Converts a list of entities to list of resources
     * @param entities to be converted
     * @return list of resources
     */
    public static List<ProductResource> toListResource(final Iterable<Product>  entities){
        final List<ProductResource> list = new ArrayList<ProductResource>();
        for (Product entity : entities) {
            list.add(toResource(entity));
        }
        return list;
    }

    /**
     * Converts an entity to a resource
     * @param entity to be converted
     * @return resource
     */
    public static ProductResource toResource(final Product entity) {
        ProductResource resource = null;

        if (entity != null) {

            resource = new ProductResource();
            resource.setProductId(entity.getProdId());
            resource.setOrderId(entity.getOrderId());
            resource.setName(entity.getName());
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
    public static Set<Product> toIterableEntity(final List<ProductResource> resources) {
        final Set<Product> set = new HashSet<Product>();

        for(ProductResource resource: resources){
            set.add(toEntity(resource));
        }
        return set;
    }

    /**
     * Converts a resource to entity
     * @param resource to be converted
     * @return entity
     */
    public static Product toEntity(ProductResource resource){
        Product entity =  null;
        if(resource != null){
            entity = new Product();
            entity.setCreateDate(Calendar.getInstance().getTime());
            entity.setOrderId(resource.getOrderId());
            entity.setName(resource.getName());
            entity.setStatus(resource.getStatus());
            entity.setStatusId(resource.getStatusId());

        }
        return entity;
    }
}
