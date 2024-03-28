package com.secondproject.shoppingproject.order.controller;

import com.secondproject.shoppingproject.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/order")
public class AdminOrderController {
    private final OrderService orderService;

}
