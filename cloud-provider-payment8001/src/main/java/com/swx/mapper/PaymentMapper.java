package com.swx.mapper;

import com.swx.entity.Payment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description:
 * @Author: sunweixin
 * @Date: 2021/12/30
 */
@Mapper
public interface PaymentMapper {
    /**
     * 添加
     * @param payment
     * @return
     */
    public int add(Payment payment);
    /**
     * 通过id查询
     */
    public Payment getPaymentById(@Param("id") Long id);
}
