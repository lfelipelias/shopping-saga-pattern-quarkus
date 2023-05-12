package com.lfefox.order.controller;

import com.lfefox.common.resource.OrderInfoResource;
import com.lfefox.order.usecase.NewOrderUserCase;
import com.lfefox.order.usecase.ProcessOrderUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

/**
 * Felipe.Elias
 */
@Slf4j
@Tag(name = "order")
@Path("/order")
@ApplicationScoped
@RequiredArgsConstructor
public class OrderController {

    private final ProcessOrderUseCase processOrderUseCase;
    private final NewOrderUserCase newOrderUserCase;

    @POST
    @Operation(summary = "Creates an order and makes product reservation in product-service")
    @APIResponse(responseCode = "200", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = OrderInfoResource.class)))
    public Response newOrder(OrderInfoResource orderResource, @Context UriInfo uriInfo) {
        return Response.ok(newOrderUserCase.newOrder(orderResource)).build();
    }


    @POST()
    @Path("process-order")
    @Operation(summary = "Process an order that is already created")
    @APIResponse(responseCode = "200", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = OrderInfoResource.class)))
    public Response processOrder(OrderInfoResource orderResource, @Context UriInfo uriInfo) {
       return Response.ok(processOrderUseCase.processOrder(orderResource)).build();
    }
}