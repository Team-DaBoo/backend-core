package b172.challenging.gathering.domain;

import b172.challenging.member.domain.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Table(name = "gathering",
    indexes = @Index(name = "idx_gathering_platform", columnList = "platform")
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Schema(description = "모임 정보")
public class Gathering {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gathering_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_member_id", nullable = false)
//    @JsonIgnore
    @Schema(description = "모임 개설자 Id")
    private Member ownerMember;

    @Column(nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    @Schema(description = "앱 플랫폼")
    private AppTechPlatform platform;

    @Column(nullable = false)
    @Schema(description = "모임 제목")
    private String title;

    @Column(name = "people_num", nullable = false)
    @Schema(description = "모집 할 인원 수")
    private int peopleNum;

    @Column(name = "goal_amount", nullable = false)
    @Schema(description = "목표 금액")
    private Long goalAmount;

    @Column(name = "working_days", nullable = false)
    @Schema(description = "기간")
    private int workingDays;

    @Column(nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private GatheringStatus status;

    @Column(name = "start_date", nullable = false)
    @Schema(description = "시작 일시")
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    @Schema(description = "만료 일시")
    private LocalDateTime endDate;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "gathering", cascade = { CascadeType.PERSIST , CascadeType.MERGE })
    private List<GatheringMember> gatheringMembers;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public void addGatheringMember(GatheringMember gatheringMember){
        gatheringMembers.add(gatheringMember);
        gatheringMember.setGathering(this);
    }

    public Gathering(Long id){
        this.id = id;
    }
}
