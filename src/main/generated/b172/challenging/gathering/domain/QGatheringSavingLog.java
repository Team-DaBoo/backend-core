package b172.challenging.gathering.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGatheringSavingLog is a Querydsl query type for GatheringSavingLog
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGatheringSavingLog extends EntityPathBase<GatheringSavingLog> {

    private static final long serialVersionUID = -379881804L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QGatheringSavingLog gatheringSavingLog = new QGatheringSavingLog("gatheringSavingLog");

    public final NumberPath<Long> amount = createNumber("amount", Long.class);

    public final DateTimePath<java.time.LocalDateTime> certificatedAt = createDateTime("certificatedAt", java.time.LocalDateTime.class);

    public final QGatheringMember gatheringMember;

    public final ListPath<GatheringSavingCertification, QGatheringSavingCertification> gatheringSavingCertifications = this.<GatheringSavingCertification, QGatheringSavingCertification>createList("gatheringSavingCertifications", GatheringSavingCertification.class, QGatheringSavingCertification.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QGatheringSavingLog(String variable) {
        this(GatheringSavingLog.class, forVariable(variable), INITS);
    }

    public QGatheringSavingLog(Path<? extends GatheringSavingLog> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QGatheringSavingLog(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QGatheringSavingLog(PathMetadata metadata, PathInits inits) {
        this(GatheringSavingLog.class, metadata, inits);
    }

    public QGatheringSavingLog(Class<? extends GatheringSavingLog> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.gatheringMember = inits.isInitialized("gatheringMember") ? new QGatheringMember(forProperty("gatheringMember"), inits.get("gatheringMember")) : null;
    }

}

