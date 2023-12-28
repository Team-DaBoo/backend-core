package b172.challenging.gathering.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGathering is a Querydsl query type for Gathering
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGathering extends EntityPathBase<Gathering> {

    private static final long serialVersionUID = -926730282L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QGathering gathering = new QGathering("gathering");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> endDate = createDateTime("endDate", java.time.LocalDateTime.class);

    public final ListPath<GatheringMember, QGatheringMember> gatheringMembers = this.<GatheringMember, QGatheringMember>createList("gatheringMembers", GatheringMember.class, QGatheringMember.class, PathInits.DIRECT2);

    public final NumberPath<Long> goalAmount = createNumber("goalAmount", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final b172.challenging.auth.domain.QMember ownerMember;

    public final NumberPath<Integer> peopleNum = createNumber("peopleNum", Integer.class);

    public final EnumPath<AppTechPlatform> platform = createEnum("platform", AppTechPlatform.class);

    public final DateTimePath<java.time.LocalDateTime> startDate = createDateTime("startDate", java.time.LocalDateTime.class);

    public final EnumPath<GatheringStatus> status = createEnum("status", GatheringStatus.class);

    public final StringPath title = createString("title");

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public final NumberPath<Integer> workingDays = createNumber("workingDays", Integer.class);

    public QGathering(String variable) {
        this(Gathering.class, forVariable(variable), INITS);
    }

    public QGathering(Path<? extends Gathering> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QGathering(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QGathering(PathMetadata metadata, PathInits inits) {
        this(Gathering.class, metadata, inits);
    }

    public QGathering(Class<? extends Gathering> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.ownerMember = inits.isInitialized("ownerMember") ? new b172.challenging.auth.domain.QMember(forProperty("ownerMember")) : null;
    }

}

