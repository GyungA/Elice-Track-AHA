package com.secondproject.shoppingproject.order.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.ui.Model;

@RestController
@RequestMapping("/order")
public class ViewController {
    @GetMapping //결제창
    public String orderPage(Model model){
        return "order/order";
    }

    @GetMapping("/mypage-order-detail") //마이페이지:주문 상세
    public String mypageOrderDatail(Model model){
        return "mypage-order-detail/mypage-order-detail";
    }

    @GetMapping("/mypage-order-detail") //마이페이지:주문 상세
    public String mypageOrderDatail(Model model){
        return "mypage-order-detail/mypage-order-detail";
    }

//    @GetMapping //결제창
//    public String orderPage(Model model){
//        return "order/order";
//    }
//
//    @GetMapping //결제창
//    public String orderPage(Model model){
//        return "order/order";
//    }

}
