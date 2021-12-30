package com.swx.controller;


import com.swx.entity.Payment;
import com.swx.service.PaymentService;
import com.swx.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description:
 * @Author: sunweixin
 * @Date: 2021/12/30
 */
@RestController
@Slf4j
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping(value = "/payment/save")
    public Result create(@RequestBody Payment payment)
    {
        int result = paymentService.save(payment);
        log.info("*****插入操作返回结果:" + result);

        if(result > 0)
        {
            return new Result(200,"插入数据库成功",result);
        }else{
            return new Result(444,"插入数据库失败",null);
        }
    }

    @GetMapping(value = "/payment/get/{id}")
    public Result<Payment> getPaymentById(@PathVariable("id") Long id)
    {
        Payment payment = paymentService.getPaymentById(id);
        log.info("*****查询结果:{}",payment);
        if (payment != null) {
            return new Result(200,"查询成功",payment);
        }else{
            return new Result(444,"没有对应记录,查询ID: "+id,null);
        }
    }

}
