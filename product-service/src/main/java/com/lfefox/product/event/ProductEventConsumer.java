package com.lfefox.product.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lfefox.common.resource.OrderInfoResource;
import com.lfefox.product.usecase.ProductUseCase;
import io.smallrye.mutiny.Uni;
import io.smallrye.reactive.messaging.kafka.Record;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.control.ActivateRequestContext;

/**
 * Felipe.Elias
 */
@Slf4j
@ApplicationScoped
@RequiredArgsConstructor
public class ProductEventConsumer {

    private final ProductUseCase productUseCase;
    @SneakyThrows
    @Incoming("product-in")
    @ActivateRequestContext
    public Uni<Void> receive(Record<Long, String> record) {

        ObjectMapper objectMapper = new ObjectMapper();

        final OrderInfoResource orderResource = objectMapper.readValue(record.value(), OrderInfoResource.class);

        log.info("receiving event of processing order");

        return productUseCase
                .processProducts(orderResource)
                .invoke(()-> log.info("END USECASE NEW PRODUCT FOR ORDER: {}: {}", orderResource));

    }

}
