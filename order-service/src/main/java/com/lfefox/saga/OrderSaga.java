package com.lfefox.saga;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lfefox.model.Order;
import com.lfefox.service.OrderService;
import io.smallrye.reactive.messaging.kafka.Record;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.jboss.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@Slf4j
@ApplicationScoped
@RequiredArgsConstructor
public class OrderSaga {


    private final OrderService orderService;

    @Inject
    @Channel("product-out")
    private Emitter<Record<Long, String>> emitter;


    @SneakyThrows
    public Order saveOrder(Order order){
        log.info("BEGIN SAGA :{}", order);

        order = orderService.saveOrder(order);
        ObjectMapper objectMapper = new ObjectMapper();

        var seatJson = objectMapper.writeValueAsString(order);

        emitter.send(Record.of(order.getId(), seatJson))
                .whenComplete((success, failure) -> {
                    if (failure != null) {
                        log.error("D'oh! " + failure.getMessage());
                    } else {
                        log.info("Message processed successfully");
                    }
                });

        return order;
    }
}
