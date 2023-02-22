package com.lfefox.saga;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lfefox.model.Order;
import com.lfefox.service.OrderService;
import io.smallrye.reactive.messaging.kafka.Record;
import lombok.SneakyThrows;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class OrderSaga {

    @Inject
    private Logger logger;
    @Inject
    private OrderService orderService;

    @Inject
    @Channel("product-out")
    private Emitter<Record<Long, String>> emitter;


    @SneakyThrows
    public Order saveOrder(Order order){
        logger.infof("BEGIN SAGA :{}", order);

        order = orderService.saveOrder(order);
        ObjectMapper objectMapper = new ObjectMapper();

        var seatJson = objectMapper.writeValueAsString(order);

        emitter.send(Record.of(order.getId(), seatJson))
                .whenComplete((success, failure) -> {
                    if (failure != null) {
                        logger.error("D'oh! " + failure.getMessage());
                    } else {
                        logger.info("Message processed successfully");
                    }
                });

        return order;
    }
}
