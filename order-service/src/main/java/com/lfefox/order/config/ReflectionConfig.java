package com.lfefox.order.config;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection(targets = {
        com.lfefox.common.resource.OrderInfoResource.class,
        com.lfefox.common.resource.OrderProductResource.class,
        com.lfefox.common.resource.PaymentResource.class,
        com.lfefox.common.resource.ProductResource.class,

})
public class ReflectionConfig {
}
