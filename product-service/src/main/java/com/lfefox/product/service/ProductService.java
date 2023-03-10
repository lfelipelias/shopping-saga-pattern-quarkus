package com.lfefox.product.service;


import com.lfefox.common.enums.ProductStatusEnum;
import com.lfefox.common.resource.OrderInfoResource;
import com.lfefox.common.resource.ProductResource;
import com.lfefox.product.entity.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

/**
 * Felipe.Elias
 */
@Slf4j
@ApplicationScoped
@RequiredArgsConstructor
public class ProductService {

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void processOrder(OrderInfoResource orderResource){
        log.info("processOrder: {}", orderResource);

        List<Product> products = Product.find("orderId", orderResource.getOrderId()).list();

        if(!ObjectUtils.isEmpty(products)){
            products.forEach(prod ->{
                prod.setStatusId(ProductStatusEnum.SOLD.getId());
                prod.setStatus(ProductStatusEnum.SOLD.name());
            });
        }

        Product.persist(products);

        log.info("setting status of products to : {}", ProductStatusEnum.SOLD.name());

    }

    @Transactional
    public List<ProductResource> listProducts(OrderInfoResource orderResource){
        log.info("listingProducts for orderId: {} with productStatus: {} ", orderResource.getOrderId(), ProductStatusEnum.SELL_IN_PROGRESS.name());

        ProductResource productOne = new ProductResource();
        productOne.setOrderId(1L);
        productOne.setProductId(1L);
        productOne.setStatus(ProductStatusEnum.SELL_IN_PROGRESS.name());
        productOne.setStatusId(ProductStatusEnum.SELL_IN_PROGRESS.getId());
        productOne.setName("GAMER MONITOR");

        ProductResource productTwo = new ProductResource();
        productTwo.setOrderId(1L);
        productTwo.setProductId(2L);
        productTwo.setStatus(ProductStatusEnum.SELL_IN_PROGRESS.name());
        productTwo.setStatusId(ProductStatusEnum.SELL_IN_PROGRESS.getId());
        productTwo.setName("SSD HD");


        return Arrays.asList(productOne, productTwo);

    }
    @Transactional
    public void saveProducts(List<ProductResource> productResources){
        log.info("saving products: {}", productResources);

    }


}
