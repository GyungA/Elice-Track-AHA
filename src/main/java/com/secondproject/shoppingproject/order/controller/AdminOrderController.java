package com.secondproject.shoppingproject.order.controller;

import com.secondproject.shoppingproject.order.dto.order.admin.AdminOrderDeleteRequestDto;
import com.secondproject.shoppingproject.order.dto.order.admin.AdminOrderUpdateRequestDto;
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
     * @return 전체 주문 내역 리스트
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
    @GetMapping("/user/{user_id}")
    public ResponseEntity<List<OrderHistoryResponseDto>> getMyOrderHistory(@PathVariable("user_id") Long userId) {
        return ResponseEntity.ok(orderService.getMyOrderHistory(userId));
    }

    //TODO: 위 api에서 주문자, 판매자를 기준으로 필터링

    /**
     * 관리자 페이지 -> 주문 상세보기에서 배송 상태를 수정 가능
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


    /**
     * 관리자 페이지에서 회원들의 주문 내역을 삭제 가능
     * @param requestDto 관리자 id, 주문 내역 삭제할 order id
     * @return "삭제" 상태로 수정한 주문의 상세 조회... 사실 이건 어떤 값을 리턴해야할지 모르겠음
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
    @PatchMapping("/delete")
    public ResponseEntity<OrderDetailHistoryResponseDto> delete(@RequestBody AdminOrderDeleteRequestDto requestDto) {
        return ResponseEntity.ok(orderService.delete(requestDto));
    }

//      TODO: 관리자 페이지에서 주문 취소 가능하게
//      TODO: 취소 신청된 주문들을 취소 완료로 상태 변경
}
