package com.secondproject.shoppingproject.order.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.ui.Model;

@Controller
@RequiredArgsConstructor
public class ViewController {
    @GetMapping ("/order/pay")//결제창
    public String orderPage(Model model){
        return "order";
    }

    @GetMapping("/order/detail") //마이페이지:주문 상세
    public String mypageOrderDatail(Model model){
        return "/mypage-order-detail";
    }

    @GetMapping("/order/management") //마이페이지:주문 관리
    public String mypageOrderManagement(Model model){
        return "/mypage-order-management";
    }

    @GetMapping("/seller/order/management") //판매자: 주문 관리
    public String sellerOrderManagement(Model model){
        return "seller-order-management";
    }

    @GetMapping("/seller/order/detail") //판매자: 주문 상세
    public String sellerOrderDetail(Model model){
        return "seller-order-detail";
    }



}
