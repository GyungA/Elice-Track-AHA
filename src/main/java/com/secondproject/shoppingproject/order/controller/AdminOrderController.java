package com.secondproject.shoppingproject.order.controller;

import com.secondproject.shoppingproject.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/orders")
public class AdminOrderController {
    private final OrderService orderService;

    /*
//    관리자 페이지에서 자신의 상품을 구매한 회원들의 주문 내역을 조회 가능
//  */
//    @GetMapping("/user/{user_id}/target/{target_id}")
//    public ResponseEntity<List<OrderHistoryResponseDto>> getMyOrderHistory(@PathVariable("user_id") Long userId,
//                                                                           @PathVariable("target_id") Long targetId){
//
//    }
//
//    /*
//    관리자 페이지 -> 주문 상세보기에서 배송 상태를 수정 가능
//     */
//    @PatchMapping
//    public ResponseEntity<OrderHistoryResponseDto> update(@RequestBody HostOrderUpdateRequestDto requestDto){
//
//    }
//
//    /*
//    관리자 페이지에서 회원들의 주문 내역을 삭제 가능
//     */
//    @PatchMapping
//    public void delete(@RequestBody HostOrderDeleteRequestDto requestDto){
//
//    }
}
