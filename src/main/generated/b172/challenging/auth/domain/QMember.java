package b172.challenging.auth.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = 1471572208L;

    public static final QMember member = new QMember("member1");

    public final NumberPath<Long> birthYear = createNumber("birthYear", Long.class);

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isLeaved = createBoolean("isLeaved");

    public final StringPath jwtCode = createString("jwtCode");

    public final DateTimePath<java.time.LocalDateTime> leavedAt = createDateTime("leavedAt", java.time.LocalDateTime.class);

    public final StringPath nickname = createString("nickname");

    public final StringPath oauthId = createString("oauthId");

    public final EnumPath<OauthProvider> oauthProvider = createEnum("oauthProvider", OauthProvider.class);

    public final EnumPath<Role> role = createEnum("role", Role.class);

    public final EnumPath<Sex> sex = createEnum("sex", Sex.class);

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}
