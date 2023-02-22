package com.lfefox.payment.service;

import com.lfefox.payment.model.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.UUID;

/**
 * Felipe.Elias
 */
@Slf4j
@ApplicationScoped
@RequiredArgsConstructor
public class PaymentService {

    @Transactional
    public Order savePayment(Order order){
        log.info("savePayment");
        return order;
    }

    @Transactional
    public Order compensatePayment(Order order){
        log.info("compensatePayment");
        return order;
    }
}
