package com.lfefox.order.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lfefox.common.enums.OrderStatusEnum;
import com.lfefox.common.enums.TransactionEventTypeEnum;
import com.lfefox.common.resource.OrderInfoResource;
import com.lfefox.common.resource.OrderProductResource;
import com.lfefox.order.events.OrderEventConsumer;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.smallrye.reactive.messaging.kafka.Record;
import io.smallrye.reactive.messaging.providers.connectors.InMemoryConnector;
import io.smallrye.reactive.messaging.providers.connectors.InMemorySource;
import org.junit.jupiter.api.Test;
import javax.enterprise.inject.Any;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.ArrayList;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

/**
 * Felipe.Elias
 */
@QuarkusTest
public class OrderTest {

    @Inject
    OrderEventConsumer orderEventConsumer;

    @Inject @Any
    InMemoryConnector connector;

    @Test
    public void testProcessOrder() {
        OrderInfoResource resource = new OrderInfoResource();
        resource.setOrderId(1L);

        given()
            .contentType(ContentType.JSON)
            .body(resource)
            .when().post("/order/process-order")
            .then()
                .statusCode(200)
                .body("status", is(OrderStatusEnum.IN_PROGRESS.name()));
    }

    @Test
    public void testNewOrder() {
        OrderInfoResource resource = new OrderInfoResource();
        resource.setTotalOrder(new BigDecimal("700"));
        resource.setUserId(1L);
        resource.setOrderProductResources(new ArrayList<>());

        OrderProductResource orderProductResource = new OrderProductResource();
        orderProductResource.setProductId(3L);
        orderProductResource.setPrice(new BigDecimal("700"));

        resource.getOrderProductResources().add(orderProductResource);

        given()
                .contentType(ContentType.JSON)
                .body(resource)
                .when().post("/order")
                .then()
                .statusCode(200)
                .body("status", is(OrderStatusEnum.CREATED.name()));
    }
    @Test
    public void testSendOrderEventConsumer() throws JsonProcessingException {
        OrderInfoResource orderInfoResource = new OrderInfoResource();
        orderInfoResource.setOrderId(1l);
        orderInfoResource.setTransactionEventType(TransactionEventTypeEnum.COMPLETE_ORDER);


        ObjectMapper objectMapper = new ObjectMapper();
        final String jsonToSend = objectMapper.writeValueAsString(orderInfoResource);

        InMemorySource<Record<Long, String>> deliveries = connector.source("order-in");
        deliveries.send(Record.of(orderInfoResource.getOrderId(), jsonToSend));
    }
}
