package b172.challenging.gathering.domain;

import b172.challenging.common.domain.BaseTimeEntity;
import b172.challenging.member.domain.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Table(name = "gathering_member")
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Schema(description = "모임 가입한 사용자")
public class GatheringMember extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gathering_member_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "group_id" ,nullable = false)
    @Schema(description = "모임 ID")
    private Gathering gathering;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    @Schema(description = "가입한 사용자 ID")
    private Member member;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    @Schema(description = "가입한 상태")
    private GatheringMemberStatus status;

    @Column(nullable = false, columnDefinition = "bigint default 0")
    @Schema(description = "모은 금액")
    private Long amount;

    @Column(nullable = false, columnDefinition = "bigint default 0")
    @Schema(description = "모은 횟수")
    private int count;

    @OneToMany(mappedBy = "gatheringMember", cascade = CascadeType.ALL)
    private List<GatheringSavingLog> gatheringSavingLogs;
    public void setGathering(Gathering gathering){
        this.gathering = gathering;
    }

    public void setStatus(GatheringMemberStatus status){
        this.status = status;
    }

    public void addSavingLog(GatheringSavingLog gatheringSavingLog){
        gatheringSavingLogs.add(gatheringSavingLog);
        gatheringSavingLog.setGatheringMember(this);
    }
}
