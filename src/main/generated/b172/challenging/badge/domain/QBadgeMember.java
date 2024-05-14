package b172.challenging.badge.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBadgeMember is a Querydsl query type for BadgeMember
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBadgeMember extends EntityPathBase<BadgeMember> {

    private static final long serialVersionUID = -1289679856L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBadgeMember badgeMember = new QBadgeMember("badgeMember");

    public final b172.challenging.common.domain.QBaseTimeEntity _super = new b172.challenging.common.domain.QBaseTimeEntity(this);

    public final QBadge badge;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final b172.challenging.member.domain.QMember member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QBadgeMember(String variable) {
        this(BadgeMember.class, forVariable(variable), INITS);
    }

    public QBadgeMember(Path<? extends BadgeMember> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBadgeMember(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBadgeMember(PathMetadata metadata, PathInits inits) {
        this(BadgeMember.class, metadata, inits);
    }

    public QBadgeMember(Class<? extends BadgeMember> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.badge = inits.isInitialized("badge") ? new QBadge(forProperty("badge")) : null;
        this.member = inits.isInitialized("member") ? new b172.challenging.member.domain.QMember(forProperty("member")) : null;
    }

}

