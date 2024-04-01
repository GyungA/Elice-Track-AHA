package com.secondproject.shoppingproject.order.controller;

import com.secondproject.shoppingproject.order.dto.order.admin.AdminOrderDeleteRequestDto;
import com.secondproject.shoppingproject.order.dto.order.admin.AdminOrderUpdateRequestDto;
import com.secondproject.shoppingproject.order.dto.order.user.OrderDetailHistoryResponseDto;
import com.secondproject.shoppingproject.order.dto.order.user.OrderHistoryResponseDto;
import com.secondproject.shoppingproject.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/orders")
public class AdminOrderController {
    private final OrderService orderService;

    /*
    관리자 페이지에서 모든 회원들의 주문 내역을 조회 가능
  */
    @GetMapping("/user/{user_id}")
    public ResponseEntity<List<OrderHistoryResponseDto>> getMyOrderHistory(@PathVariable("user_id") Long userId) {
        return ResponseEntity.ok(orderService.getMyOrderHistory(userId));
    }

    //위 api에서 주문자, 판매자를 기준으로 필터링

    /*
    관리자 페이지 -> 주문 상세보기에서 배송 상태를 수정 가능
     */
    @PatchMapping
    public ResponseEntity<OrderDetailHistoryResponseDto> update(@RequestBody AdminOrderUpdateRequestDto requestDto) {
        return ResponseEntity.ok(orderService.update(requestDto));
    }

    /*
    관리자 페이지에서 회원들의 주문 내역을 삭제 가능
     */
    @PatchMapping
    public ResponseEntity<OrderDetailHistoryResponseDto> delete(@RequestBody AdminOrderDeleteRequestDto requestDto) {
        return ResponseEntity.ok(orderService.delete(requestDto));
    }

//      TODO: 취소 신청된 주문들을 취소 완료로 상태 변경
}
