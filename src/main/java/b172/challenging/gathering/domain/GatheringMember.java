package b172.challenging.gathering.domain;

import b172.challenging.member.domain.Member;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties({
        "createdAt"
})
public class GatheringMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gathering_member_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id" ,nullable = false)
    @Schema(description = "모임 ID")
    @JsonIgnore
    private Gathering gathering;

    @ManyToOne(fetch = FetchType.LAZY)
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

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "gatheringMember", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<GatheringSavingLog> gatheringSavingLog;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
    public void setGathering(Gathering gathering){
        this.gathering = gathering;
    }

    public void setStatus(GatheringMemberStatus status){
        this.status = status;
    }

}
