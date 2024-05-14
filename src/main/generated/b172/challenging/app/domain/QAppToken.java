package b172.challenging.app.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAppToken is a Querydsl query type for AppToken
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAppToken extends EntityPathBase<AppToken> {

    private static final long serialVersionUID = 940855107L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAppToken appToken = new QAppToken("appToken");

    public final b172.challenging.common.domain.QBaseTimeEntity _super = new b172.challenging.common.domain.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath device = createString("device");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final b172.challenging.member.domain.QMember member;

    public final BooleanPath notificationAgree = createBoolean("notificationAgree");

    public final StringPath token = createString("token");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QAppToken(String variable) {
        this(AppToken.class, forVariable(variable), INITS);
    }

    public QAppToken(Path<? extends AppToken> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAppToken(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAppToken(PathMetadata metadata, PathInits inits) {
        this(AppToken.class, metadata, inits);
    }

    public QAppToken(Class<? extends AppToken> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new b172.challenging.member.domain.QMember(forProperty("member")) : null;
    }

}

