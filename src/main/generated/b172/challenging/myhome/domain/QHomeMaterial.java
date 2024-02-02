package b172.challenging.myhome.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QHomeMaterial is a Querydsl query type for HomeMaterial
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QHomeMaterial extends EntityPathBase<HomeMaterial> {

    private static final long serialVersionUID = 1346934751L;

    public static final QHomeMaterial homeMaterial = new QHomeMaterial("homeMaterial");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final NumberPath<Long> needed = createNumber("needed", Long.class);

    public QHomeMaterial(String variable) {
        super(HomeMaterial.class, forVariable(variable));
    }

    public QHomeMaterial(Path<? extends HomeMaterial> path) {
        super(path.getType(), path.getMetadata());
    }

    public QHomeMaterial(PathMetadata metadata) {
        super(HomeMaterial.class, metadata);
    }

}

