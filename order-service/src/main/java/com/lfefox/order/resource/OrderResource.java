package com.lfefox.order.resource;

import com.lfefox.common.model.Order;
import com.lfefox.order.usecase.NewOrderUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

@Slf4j
@Path("/order")
@ApplicationScoped
@RequiredArgsConstructor
public class OrderResource {

    private final NewOrderUseCase orderSaga;



    @POST
    public Response saveOrder(Order order, @Context UriInfo uriInfo) {

        order = orderSaga.saveOrder(order);
        UriBuilder builder = uriInfo.getAbsolutePathBuilder().path(order.getOrderUuid());

        log.info("New Order " + builder.build().toString());
        return Response.created(builder.build()).build();
    }
}
