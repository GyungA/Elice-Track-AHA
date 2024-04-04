package com.secondproject.shoppingproject.order.controller;

import com.secondproject.shoppingproject.order.dto.order.user.*;
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
import jakarta.validation.Valid;
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
     *
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
     *
     * @param userId  자기자신의 user id
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

    /**
     * 결제하기
     * @param requestDto
     * @return
     */
    @PostMapping("/pay")
    public ResponseEntity<OrderDetailHistoryResponseDto> payProduct(@Valid @RequestBody PayProductRequestDto requestDto) {
        return ResponseEntity.ok(orderService.payProduct(requestDto));
    }

    /**
     * 결제 정보 조회
     * @param userId
     * @param orderId
     * @return
     */
    @GetMapping("/pay/user/{user_id}/order/{order_id}")
    public ResponseEntity<OrderPayResponseDto> getPayInfo(@PathVariable("user_id") Long userId,
                                                          @PathVariable("order_id") Long orderId) {
        //TODO: 추후 redis 이용?
        return ResponseEntity.ok(orderService.getPayInfo(userId, orderId));
    }

    /**
     * 상품을 구매하기 위해 상품 결제창으로 넘기기
     * @param requestDto
     * @return
     */
    @PostMapping("/buy")
    public ResponseEntity<Long> buyProduct(@RequestBody BuyProductRequestDto requestDto) {
        //TODO: 추후 redis 이용?
        return ResponseEntity.ok(orderService.buyProduct(requestDto));
    }

    /**
     * 주문 완료 후, "주문 완료" 상태일 때만 주문 정보 수정 가능(배송지, 수령인 이름, 수령인 연락처)
     * @param requestDto 유저 id, 수정을 원하는 order id, 배송지, 수령인 이름, 수령인 연락처
     * @return 수정한 주문 상세 조회
     */
    @Operation(summary = "주문 정보 수정", description = "주문 완료 상태일 때만 주문 정보(배송지, 수령인 이름, 수령인 연락처에 한해) 수정 가능")
    @Parameter(name = "requestDto", description = "유저 id, 수정을 원하는 order id, 배송지, 수령인 이름, 수령인 연락처")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = OrderDetailHistoryResponseDto.class))}),
            @ApiResponse(responseCode = "403", description = "해당 주문을 수정할 권한이 없습니다."),
            @ApiResponse(responseCode = "404", description = "해당 ID의 주문이 존재하지 않습니다."),
            @ApiResponse(responseCode = "409", description = "이미 상품이 배송 중이거나 도착하였기 때문에 정보 수정이 불가합니다."),
    })
    @PatchMapping
    public ResponseEntity<OrderDetailHistoryResponseDto> update(@Valid @RequestBody OrderUpdateRequestDto requestDto) {
        //전과 달라진 게 없는 값의 경우, null값을 보내는지 or 원래 값 그대로 보내는지?
        //일단 null 값 보내는 걸로 구현
        return ResponseEntity.ok(orderService.update(requestDto));
    }


    /**
     * 주문 완료 후, "배송중" 상태 전까지 주문 취소 가능
     * @param requestDto 유저 id, 주문 취소할 order id
     * @return 취소한 주문 상세 조회
     */
    @Operation(summary = "주문 취소", description = "주문 완료 상태일 때만 주문 취소가 가능합니다.")
    @Parameter(name = "requestDto", description = "유저 id, 주문 취소할 order id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = OrderDetailHistoryResponseDto.class))}),
            @ApiResponse(responseCode = "403", description = "해당 주문을 취소할 권한이 업습니다."),
            @ApiResponse(responseCode = "404", description = "해당 ID의 주문이 존재하지 않습니다."),
    })
    @PatchMapping("/cancel")
    public ResponseEntity<OrderDetailHistoryResponseDto> cancel(@RequestBody OrderCancelRequestDto requestDto) {
        return ResponseEntity.ok(orderService.cancel(requestDto));
    }
}
