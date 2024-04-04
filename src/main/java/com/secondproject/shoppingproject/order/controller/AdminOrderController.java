package com.secondproject.shoppingproject.order.controller;

import com.secondproject.shoppingproject.order.dto.order.admin.AdminOrderDeleteRequestDto;
import com.secondproject.shoppingproject.order.dto.order.admin.AdminOrderUpdateRequestDto;
import com.secondproject.shoppingproject.order.dto.order.user.OrderCancelRequestDto;
import com.secondproject.shoppingproject.order.dto.order.user.OrderDetailHistoryResponseDto;
import com.secondproject.shoppingproject.order.dto.order.user.OrderHistoryResponseDto;
import com.secondproject.shoppingproject.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/orders")
public class AdminOrderController {
    private final OrderService orderService;

    /**
     * 관리자 페이지에서 모든 회원들의 주문 내역을 조회 가능
     * @param userId 관리자 아이디
     * @param sellerId 판매자 기준으로 필터링하고 싶다면, 판매자 아이디 입력
     * @param buyerId 구매자 기준으로 필터링하고 싶다면, 판매자 아이디 입력
     * @return 전체 주문 내역 리스트
     */
    @Operation(summary = "모든 주문 내역 조회", description = "자신의 모든 주문 내역을 조회합니다.\n" +
            "request param은 필수 아님. 비워둔다면, 필터링 없이 모든 주문 내역 조회")
    @Parameters({
            @Parameter(name = "user_id", description = "자신의 아이디"),
            @Parameter(name = "seller_id", description = "판매자 기준으로 필터링하고 싶다면, 판매자 아이디 입력"),
            @Parameter(name = "buyer_id", description = "구매자 기준으로 필터링하고 싶다면, 판매자 아이디 입력"),
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = OrderDetailHistoryResponseDto.class))}),
            @ApiResponse(responseCode = "404", description = "해당 유저 ID 또는 Order Id가 존재하지 않습니다."),
    })
    @GetMapping("/user/{user_id}")
    public ResponseEntity<List<OrderHistoryResponseDto>> getOrderHistory(
            @PathVariable("user_id") Long userId,
            @RequestParam(value = "seller_id", required = false) Long sellerId,
            @RequestParam(value = "buyer_id", required = false) Long buyerId){

        return ResponseEntity.ok(orderService.getOrderHistory(userId, sellerId, buyerId));
    }

    /**
     * 관리자 페이지 -> 주문 상세보기에서 배송 상태를 수정 가능
     *
     * @param requestDto 관리자 id, 상태 수정할 order id, 주문 상태
     * @return 상태 수정한 주문의 상세 조회
     */
    @Operation(summary = "모든 주문 내역 조회", description = "자신의 모든 주문 내역을 조회합니다.")
    @Parameters({
            @Parameter(name = "userId", description = "자신의 아이디"),
            @Parameter(name = "orderId", description = "상세 조회를 원하는 주문의 아이디")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = OrderDetailHistoryResponseDto.class))}),
            @ApiResponse(responseCode = "404", description = "해당 유저 ID 또는 Order Id가 존재하지 않습니다."),
    })
    @PatchMapping("/modify-status")
    public ResponseEntity<OrderDetailHistoryResponseDto> update(@RequestBody AdminOrderUpdateRequestDto requestDto) {
        return ResponseEntity.ok(orderService.update(requestDto));
    }

//    /**
//     * 관리자 페이지에서 회원들의 주문 내역을 삭제 가능
//     *
//     * @param requestDto 관리자 id, 주문 내역 삭제할 order id
//     * @return "삭제" 상태로 수정한 주문의 상세 조회... 사실 이건 어떤 값을 리턴해야할지 모르겠음
//     */
//    @Operation(summary = "모든 주문 내역 조회", description = "자신의 모든 주문 내역을 조회합니다.")
//    @Parameters({
//            @Parameter(name = "userId", description = "자신의 아이디"),
//            @Parameter(name = "orderId", description = "상세 조회를 원하는 주문의 아이디")
//    })
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "성공",
//                    content = {@Content(schema = @Schema(implementation = OrderDetailHistoryResponseDto.class))}),
//            @ApiResponse(responseCode = "404", description = "해당 유저 ID 또는 Order Id가 존재하지 않습니다."),
//    })
//    @PatchMapping("/delete")
//    public ResponseEntity<OrderDetailHistoryResponseDto> delete(@RequestBody AdminOrderDeleteRequestDto requestDto) {
//        return ResponseEntity.ok(orderService.delete(requestDto));
//    }

    /**
     * 관리자 페이지에서 회원들의 주문 최소 가능 / 취소 신청된 주문들을 취소 완료로 상태 변경
     * @param requestDto 관리자 id, 주문 취소할 order id
     * @return 취소한 주문 상세 조회
     */
    @Operation(summary = "주문 취소", description = "주문 취소가 가능합니다.")
    @Parameter(name = "requestDto", description = "유저 id, 주문 취소할 order id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = OrderDetailHistoryResponseDto.class))}),
            @ApiResponse(responseCode = "403", description = "해당 주문을 취소할 권한이 업습니다."),
            @ApiResponse(responseCode = "404", description = "해당 ID의 주문 또는 유저가 존재하지 않습니다."),
    })
    @PatchMapping("/cancel")
    public ResponseEntity<OrderDetailHistoryResponseDto> cancel(@RequestBody OrderCancelRequestDto requestDto) {
        return ResponseEntity.ok(orderService.adminCancel(requestDto));
    }
}
