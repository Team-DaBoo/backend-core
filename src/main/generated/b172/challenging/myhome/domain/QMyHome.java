package b172.challenging.myhome.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMyHome is a Querydsl query type for MyHome
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMyHome extends EntityPathBase<MyHome> {

    private static final long serialVersionUID = -920377308L;

    public static final QMyHome myHome = new QMyHome("myHome");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> level = createNumber("level", Long.class);

    public final StringPath name = createString("name");

    public QMyHome(String variable) {
        super(MyHome.class, forVariable(variable));
    }

    public QMyHome(Path<? extends MyHome> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMyHome(PathMetadata metadata) {
        super(MyHome.class, metadata);
    }

}

