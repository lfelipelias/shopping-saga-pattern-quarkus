package com.lfefox.product.service;


import com.lfefox.common.enums.ProductStatusEnum;
import com.lfefox.common.resource.OrderResource;
import com.lfefox.common.resource.ProductModel;
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
    public void processOrder(OrderResource orderResource){
        log.info("processOrder: {}", orderResource);

        final List<ProductModel> listProductModels = listProducts(orderResource);
        log.info("found list of products for order: {}", listProductModels);

        listProductModels.stream().forEach(productModel -> {
            productModel.setStatusId(ProductStatusEnum.SOLD.getId());
            productModel.setStatus(ProductStatusEnum.SOLD.name());
        });

        log.info("setting status of products to : {}", ProductStatusEnum.SOLD.name());

        saveProducts(listProductModels);
        
    }

    @Transactional
    public List<ProductModel> listProducts(OrderResource orderResource){
        log.info("listingProducts for orderId: {} with productStatus: {} ", orderResource.getOrderId(), ProductStatusEnum.SELL_IN_PROGRESS.name());

        ProductModel productOne = new ProductModel();
        productOne.setOrderId(1L);
        productOne.setProductId(1L);
        productOne.setStatus(ProductStatusEnum.SELL_IN_PROGRESS.name());
        productOne.setStatusId(ProductStatusEnum.SELL_IN_PROGRESS.getId());
        productOne.setName("MONITOR GAMER");

        ProductModel productTwo = new ProductModel();
        productTwo.setOrderId(1L);
        productTwo.setProductId(2L);
        productTwo.setStatus(ProductStatusEnum.SELL_IN_PROGRESS.name());
        productTwo.setStatusId(ProductStatusEnum.SELL_IN_PROGRESS.getId());
        productTwo.setName("SSD HD");


        return Arrays.asList(productOne, productTwo);

    }
    @Transactional
    public void saveProducts(List<ProductModel> productModels){
        log.info("saving products: {}", productModels);

    }


}
