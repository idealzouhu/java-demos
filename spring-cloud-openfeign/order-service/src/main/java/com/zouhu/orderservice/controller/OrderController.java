package com.zouhu.orderservice.controller;

import com.zouhu.orderservice.remote.PayRemoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zouhu
 * @data 2024-09-06 19:50
 */
@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private PayRemoteService payRemoteService;

    /**
     * 创建订单并调用支付服务
     * <p>
     *     调用方法链接： http://localhost:9000/order/create
     *     Nacos控制台访问链接： http://localhost:8848/nacos/index.html
     * </p>
     *
     * @return
     */
    @GetMapping("/create")
    public String createOrder() {
        String paymentStatus = payRemoteService.processPayment();
        return "Order created! Payment status: " + paymentStatus;
    }

}
