package com.lfefox.resource;

import com.lfefox.model.Order;
import com.lfefox.saga.OrderSaga;
import org.jboss.logging.Logger;


import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

@ApplicationScoped
@Path("/order")
public class OrderResource {

    private OrderSaga orderSaga;
    private Logger logger;

    public OrderResource(OrderSaga orderSaga, Logger logger) {
        this.orderSaga = orderSaga;
        this.logger = logger;
    }

    @POST
    public Response saveOrder(Order order, @Context UriInfo uriInfo) {

        order = orderSaga.saveOrder(order);
        UriBuilder builder = uriInfo.getAbsolutePathBuilder().path(order.getOrderUuid());

        logger.info("New Order " + builder.build().toString());
        return Response.created(builder.build()).build();
    }
}
