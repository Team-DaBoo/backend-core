package b172.challenging.protip.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProTip is a Querydsl query type for ProTip
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProTip extends EntityPathBase<ProTip> {

    private static final long serialVersionUID = 782423850L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProTip proTip = new QProTip("proTip");

    public final b172.challenging.common.domain.QBaseTimeEntity _super = new b172.challenging.common.domain.QBaseTimeEntity(this);

    public final StringPath appLinkUrl = createString("appLinkUrl");

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imgUrl = createString("imgUrl");

    public final b172.challenging.member.domain.QMember registerId;

    public final StringPath title = createString("title");

    public final EnumPath<ProTipType> type = createEnum("type", ProTipType.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final EnumPath<b172.challenging.common.domain.UseYn> useYn = createEnum("useYn", b172.challenging.common.domain.UseYn.class);

    public QProTip(String variable) {
        this(ProTip.class, forVariable(variable), INITS);
    }

    public QProTip(Path<? extends ProTip> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProTip(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProTip(PathMetadata metadata, PathInits inits) {
        this(ProTip.class, metadata, inits);
    }

    public QProTip(Class<? extends ProTip> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.registerId = inits.isInitialized("registerId") ? new b172.challenging.member.domain.QMember(forProperty("registerId")) : null;
    }

}

