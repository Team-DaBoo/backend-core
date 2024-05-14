package b172.challenging.gathering.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGatheringMember is a Querydsl query type for GatheringMember
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGatheringMember extends EntityPathBase<GatheringMember> {

    private static final long serialVersionUID = 584775184L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QGatheringMember gatheringMember = new QGatheringMember("gatheringMember");

    public final b172.challenging.common.domain.QBaseTimeEntity _super = new b172.challenging.common.domain.QBaseTimeEntity(this);

    public final NumberPath<Long> amount = createNumber("amount", Long.class);

    public final NumberPath<Integer> count = createNumber("count", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final QGathering gathering;

    public final ListPath<GatheringSavingLog, QGatheringSavingLog> gatheringSavingLogs = this.<GatheringSavingLog, QGatheringSavingLog>createList("gatheringSavingLogs", GatheringSavingLog.class, QGatheringSavingLog.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final b172.challenging.member.domain.QMember member;

    public final EnumPath<GatheringMemberStatus> status = createEnum("status", GatheringMemberStatus.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QGatheringMember(String variable) {
        this(GatheringMember.class, forVariable(variable), INITS);
    }

    public QGatheringMember(Path<? extends GatheringMember> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QGatheringMember(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QGatheringMember(PathMetadata metadata, PathInits inits) {
        this(GatheringMember.class, metadata, inits);
    }

    public QGatheringMember(Class<? extends GatheringMember> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.gathering = inits.isInitialized("gathering") ? new QGathering(forProperty("gathering"), inits.get("gathering")) : null;
        this.member = inits.isInitialized("member") ? new b172.challenging.member.domain.QMember(forProperty("member")) : null;
    }

}

