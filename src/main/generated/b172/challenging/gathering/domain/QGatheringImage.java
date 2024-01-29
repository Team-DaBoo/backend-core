package b172.challenging.gathering.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QGatheringImage is a Querydsl query type for GatheringImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGatheringImage extends EntityPathBase<GatheringImage> {

    private static final long serialVersionUID = -2062813403L;

    public static final QGatheringImage gatheringImage = new QGatheringImage("gatheringImage");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath url = createString("url");

    public QGatheringImage(String variable) {
        super(GatheringImage.class, forVariable(variable));
    }

    public QGatheringImage(Path<? extends GatheringImage> path) {
        super(path.getType(), path.getMetadata());
    }

    public QGatheringImage(PathMetadata metadata) {
        super(GatheringImage.class, metadata);
    }

}

