package com.secondproject.shoppingproject.order.entity;

import com.secondproject.shoppingproject.global.entity.BaseEntity;
import com.secondproject.shoppingproject.order.status.OrderStatus;
import com.secondproject.shoppingproject.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "order")
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private int totalPayment;

    @Column(nullable = true)
    private String deliveryAddress;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus orderStatus;

    @Column(nullable = true)
    private String receiverName;

    @Column(nullable = true)
    private String receiverPhoneNumber;

    @Builder
    public Order(User user, /*String deliveryAddress,*/ OrderStatus orderStatus,
                 /*String receiverName, String receiverPhoneNumber, */int totalPayment) {
        this.user = user;
//        this.deliveryAddress = deliveryAddress;
        this.orderStatus = orderStatus;
//        this.receiverName = receiverName;
        this.totalPayment = totalPayment;
        this.receiverPhoneNumber = receiverPhoneNumber;
    }

}
