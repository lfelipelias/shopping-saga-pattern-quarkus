package com.lfefox.resource;

import com.lfefox.model.Order;
import com.lfefox.saga.OrderSaga;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jboss.logging.Logger;
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

    private final OrderSaga orderSaga;



    @POST
    public Response saveOrder(Order order, @Context UriInfo uriInfo) {

        order = orderSaga.saveOrder(order);
        UriBuilder builder = uriInfo.getAbsolutePathBuilder().path(order.getOrderUuid());

        log.info("New Order " + builder.build().toString());
        return Response.created(builder.build()).build();
    }
}
