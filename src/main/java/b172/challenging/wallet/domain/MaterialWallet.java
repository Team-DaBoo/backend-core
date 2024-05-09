package b172.challenging.wallet.domain;

import b172.challenging.myhome.domain.HomeMaterial;
import b172.challenging.member.domain.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "material_wallet")
@NoArgsConstructor
@Schema(description = "사용자 모은 자재")
public class MaterialWallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "material_wallet_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    @Schema(description = "사용자 아이디")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "home_material_id", nullable = false)
    @Schema(description = "집 만들 재료 종류 Id")
    private HomeMaterial homeMaterial;

    @Column(name = "collected", nullable = false, columnDefinition = "bigint default 0")
    @Schema(description = "모은 양")
    private Long collected;

    @Builder
    public MaterialWallet(Long id, Member member, HomeMaterial homeMaterial, Long collected) {
        this.id = id;
        this.member = member;
        this.homeMaterial = homeMaterial;
        this.collected = collected;
    }

    public void saveMaterial(Long amount) {
        this.collected += amount;
    }
}
