package com.secondproject.shoppingproject.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = -1075195450L;

    public static final QUser user = new QUser("user");

    public final com.secondproject.shoppingproject.global.entity.QBaseEntity _super = new com.secondproject.shoppingproject.global.entity.QBaseEntity(this);

    public final StringPath address = createString("address");

    public final StringPath birthdate = createString("birthdate");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath email = createString("email");

    public final EnumPath<com.secondproject.shoppingproject.user.Enum.Grade> grade = createEnum("grade", com.secondproject.shoppingproject.user.Enum.Grade.class);

    public final StringPath name = createString("name");

    public final StringPath password = createString("password");

    public final StringPath phone = createString("phone");

    public final EnumPath<com.secondproject.shoppingproject.user.Enum.Role> role = createEnum("role", com.secondproject.shoppingproject.user.Enum.Role.class);

    public final StringPath status = createString("status");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final NumberPath<Long> user_id = createNumber("user_id", Long.class);

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

