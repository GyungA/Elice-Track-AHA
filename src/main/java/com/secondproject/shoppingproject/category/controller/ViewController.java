package com.secondproject.shoppingproject.category.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ViewController {
    @GetMapping ("/order/pay")
    public String orderPage(){
        return "order1";
    }

    @GetMapping("order/detail") //마이페이지:주문 상세
    public String mypageOrderDatail(Model model){
        return "/mypage-order-detail";
    }

    @GetMapping("order/management") //마이페이지:주문 관리
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