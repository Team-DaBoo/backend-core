package b172.challenging.wallet.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMaterialWallet is a Querydsl query type for MaterialWallet
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMaterialWallet extends EntityPathBase<MaterialWallet> {

    private static final long serialVersionUID = 1872784231L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMaterialWallet materialWallet = new QMaterialWallet("materialWallet");

    public final NumberPath<Long> collected = createNumber("collected", Long.class);

    public final b172.challenging.myhome.domain.QHomeMaterial homeMaterial;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final b172.challenging.member.domain.QMember member;

    public QMaterialWallet(String variable) {
        this(MaterialWallet.class, forVariable(variable), INITS);
    }

    public QMaterialWallet(Path<? extends MaterialWallet> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMaterialWallet(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMaterialWallet(PathMetadata metadata, PathInits inits) {
        this(MaterialWallet.class, metadata, inits);
    }

    public QMaterialWallet(Class<? extends MaterialWallet> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.homeMaterial = inits.isInitialized("homeMaterial") ? new b172.challenging.myhome.domain.QHomeMaterial(forProperty("homeMaterial"), inits.get("homeMaterial")) : null;
        this.member = inits.isInitialized("member") ? new b172.challenging.member.domain.QMember(forProperty("member")) : null;
    }

}

