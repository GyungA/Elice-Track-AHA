package com.secondproject.shoppingproject.order.controller;

import com.secondproject.shoppingproject.order.dto.user.*;
import com.secondproject.shoppingproject.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "order API", description = "주문 관련 api입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    /**
     * 자신의 모든 주문 내역 조회
     * @param userId 자기자신의 user id
     */
    @Operation(summary = "모든 주문 내역 조회", description = "자신의 모든 주문 내역을 조회합니다.")
    @Parameter(name = "userId", description = "주문 내역을 조회할 유저의 아이디")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {
                            @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = OrderHistoryResponseDto.class)))
                    }),
            @ApiResponse(responseCode = "404", description = "해당 ID의 유저가 존재하지 않습니다."),
    })
    @GetMapping("/user/{user_id}")
    public ResponseEntity<List<OrderHistoryResponseDto>> getMyOrder(@PathVariable("user_id") Long userId) {
        return ResponseEntity.ok(orderService.getMyOrder(userId));
    }

    /**
     * 자신의 주문 내역 중, 하나를 상세 조회
     * @param userId 자기자신의 user id
     * @param orderId 자신의 주문 내역 중, 상세 조회하고 싶은 주문의 id
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
    @GetMapping("/user/{user_id}/order/{order_id}")
    public ResponseEntity<OrderDetailHistoryResponseDto> getDetailOrder(@PathVariable("user_id") Long userId,
                                                                              @PathVariable("order_id") Long orderId) {
        return ResponseEntity.ok(orderService.getDetailOrder(userId, orderId));
    }

    /*
    장바구니에 담긴 상품들을 주문
     */
    /*@PostMapping("/user/{user_id}")
    public ResponseEntity<OrderDetailHistoryResponseDto> orderProductInCart(@PathVariable("user_id") Long userId){
        return ResponseEntity.ok(orderService.orderProductInCart(userId));
    }*/

    /**
     * 상품을 장바구니에 넣지 않고, 바로 구매
     * @param requestDto 주문자 id, 상품 id, 상품 수량, 배송지, 수령인 이름, 수령인 연락처
     * @return 주문한 상품 상세 조회
     */
    @Operation(summary = "상품 바로 구매", description = "상품을 장바구니에 넣지 않고, 바로 구매합니다.")
    @Parameter(name = "requestDto", description = "주문 내역을 조회할 유저의 아이디")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = OrderDetailHistoryResponseDto.class))}),
            @ApiResponse(responseCode = "404", description = "해당 ID의 유저가 존재하지 않습니다."),
    })
    @PostMapping("/buy-instantly")
    public ResponseEntity<OrderDetailHistoryResponseDto> orderProductInstantly(@RequestBody OrderInstantRequestDto requestDto){
        //TODO: 배송지, 수령인 이름, 연락처 유효성 검사
        return ResponseEntity.ok(orderService.orderProductInstantly(requestDto));
    }

    /*
    주문 완료 후, 배송 전까지 주문 정보 수정 가능(요청 사항, 배송지)
     */
    /*
    @PatchMapping
    public ResponseEntity<OrderDetailHistoryResponseDto> update(@RequestBody OrderUpdateRequestDto requestDto){
        return ResponseEntity.ok(orderService.update(requestDto));
    }*/

    /*
    주문 완료 후, "배송중" 상태 전까지 주문 취소 가능
     */
    /*@PatchMapping("/cancel")
    public ResponseEntity<OrderDetailHistoryResponseDto> cancel(@RequestBody OrderCancelRequestDto requestDto){
        return ResponseEntity.ok(orderService.cancel(requestDto));
    }*/
}
