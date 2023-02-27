package com.lfefox.product.service;


import com.lfefox.common.enums.ProductStatusEnum;
import com.lfefox.common.model.Order;
import com.lfefox.common.model.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @Transactional
    public void processOrder(Order order){
        log.info("processOrder: {}", order);

        final List<Product> listProducts = listProducts(order);
        log.info("found list of products for order: {}", listProducts);

        listProducts.stream().forEach(product -> {
            product.setStatusId(ProductStatusEnum.SOLD.getId());
            product.setStatus(ProductStatusEnum.SOLD.name());
        });

        log.info("setting status of products to : {}", ProductStatusEnum.SOLD.name());

        saveProducts(listProducts);
        
    }

    @Transactional
    public List<Product> listProducts(Order order){
        log.info("listingProducts for orderId: {} with productStatus: {} ", order.getOrderId(), ProductStatusEnum.SELL_IN_PROGRESS.name());

        Product productOne = new Product();
        productOne.setOrderId(1L);
        productOne.setProductId(1L);
        productOne.setStatus(ProductStatusEnum.SELL_IN_PROGRESS.name());
        productOne.setStatusId(ProductStatusEnum.SELL_IN_PROGRESS.getId());
        productOne.setName("MONITOR GAMER");

        Product productTwo = new Product();
        productTwo.setOrderId(1L);
        productTwo.setProductId(2L);
        productTwo.setStatus(ProductStatusEnum.SELL_IN_PROGRESS.name());
        productTwo.setStatusId(ProductStatusEnum.SELL_IN_PROGRESS.getId());
        productTwo.setName("SSD HD");


        return Arrays.asList(productOne, productTwo);

    }
    @Transactional
    public void saveProducts(List<Product> products){
        log.info("saving products: {}", products);

    }


}
