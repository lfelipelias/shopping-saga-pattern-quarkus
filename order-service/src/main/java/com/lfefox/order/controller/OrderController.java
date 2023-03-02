package com.lfefox.order.controller;

import com.lfefox.common.resource.OrderInfoResource;
import com.lfefox.order.usecase.ProcessOrderUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

/**
 * Felipe.Elias
 */
@Slf4j
@Path("/order")
@ApplicationScoped
@RequiredArgsConstructor
public class OrderController {

    private final ProcessOrderUseCase orderSaga;


    @POST()
    @Path("process-order")
    public Response processOrder(OrderInfoResource orderResource, @Context UriInfo uriInfo) {
       return Response.ok(orderSaga.processOrder(orderResource)).build();
    }
}
