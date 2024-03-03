package b172.challenging.wallet.domain;

import b172.challenging.myhome.domain.MyHome;
import b172.challenging.member.domain.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "wallet")
@NoArgsConstructor
@Schema(description = "사용자 포인트")
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wallet_id")
    private Long id;

    @Schema(description = "Member id")
    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Schema(description = "MyHome id")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "home_id", columnDefinition = "bigint default 1") // FIXME: home 정보 추가 후 nullable=false로 변경
    private MyHome myHome;

    @Schema(description = "나의 집 이름")
    @Column(name = "home_name", length = 30)
    private String homeName;

    @Schema(description = "현재 포인트")
    @Column(nullable = false, columnDefinition = "bigint default 0")
    private Long point;

    @Schema(description = "전체 모은 돈")
    @Column(name = "save_amount", nullable = false, columnDefinition = "bigint default 0")
    private Long saveAmount;

    @Schema(description = "집 업데이트 날짜")
    @Column(name = "home_updated_at")
    private LocalDateTime updatedAt;


    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }


    public void savePoint(Long amount){
        this.saveAmount += amount;
        this.point += amount;
    }

    @Builder
    public Wallet(Long id, String homeName, Long point, Long saveAmount, Member member, MyHome myHome) {
        this.id = id;
        this.homeName = homeName;
        this.point = point;
        this.saveAmount = saveAmount;
        this.member = member;
        this.myHome = myHome;
    }
}
