package com.secondproject.shoppingproject.order.entity;

import com.secondproject.shoppingproject.order.status.OrderStatus;
import com.secondproject.shoppingproject.product.entity.Product;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "orderDetail")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_detail_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private int amount;

    @Column(nullable = false)
    private int payment;

    @Builder
    public OrderDetail(Order order, Product product, int quantity, int payment) {
        this.order = order;
        this.product = product;
        this.amount = quantity;
        this.payment = payment;
    }
}
