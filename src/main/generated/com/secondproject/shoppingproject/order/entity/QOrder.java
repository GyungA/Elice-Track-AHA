package com.secondproject.shoppingproject.order.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrder is a Querydsl query type for Order
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrder extends EntityPathBase<Order> {

    private static final long serialVersionUID = 1624979350L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrder order = new QOrder("order1");

    public final com.secondproject.shoppingproject.global.entity.QBaseEntity _super = new com.secondproject.shoppingproject.global.entity.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath deliveryAddress = createString("deliveryAddress");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<com.secondproject.shoppingproject.order.status.OrderStatus> orderStatus = createEnum("orderStatus", com.secondproject.shoppingproject.order.status.OrderStatus.class);

    public final StringPath receiverName = createString("receiverName");

    public final StringPath receiverPhoneNumber = createString("receiverPhoneNumber");

    public final NumberPath<Integer> totalPayment = createNumber("totalPayment", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final com.secondproject.shoppingproject.user.entity.QUser user;

    public QOrder(String variable) {
        this(Order.class, forVariable(variable), INITS);
    }

    public QOrder(Path<? extends Order> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrder(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrder(PathMetadata metadata, PathInits inits) {
        this(Order.class, metadata, inits);
    }

    public QOrder(Class<? extends Order> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.secondproject.shoppingproject.user.entity.QUser(forProperty("user")) : null;
    }

}

