package com.swx.service;

import com.swx.entity.Payment;

/**
 * @Description:
 * @Author: sunweixin
 * @Date: 2021/12/30
 */
public interface PaymentService {

    public int save(Payment payment);

    public Payment getPaymentById(Long id);
}
