package com.lfefox.order.controller;

import com.lfefox.common.enums.OrderStatusEnum;
import com.lfefox.common.resource.OrderInfoResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

/**
 * Felipe.Elias
 */
@QuarkusTest
public class OrderControllerTest {

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
}
