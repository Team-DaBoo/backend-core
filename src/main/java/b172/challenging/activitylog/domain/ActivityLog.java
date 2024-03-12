package b172.challenging.activitylog.domain;

import b172.challenging.member.domain.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@Table(name = "activity_log")
@NoArgsConstructor
@AllArgsConstructor
public class ActivityLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "activity_log_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    @Schema(description = "유저 ID")
    private Member member;

    @Column(name = "activity_category", nullable = false, length = 128)
    @Enumerated(EnumType.STRING)
    @Schema(description = "활동 카테고리")
    private ActivityCategory activityCategory;

    @Column(name = "activity_type", nullable = false, length = 128)
    @Enumerated(EnumType.STRING)
    @Schema(description = "활동 타입")
    private ActivityType activityType;

    @Column(name = "description", nullable = false, length = 128)
    @Schema(description = "설명")
    private String description;


    @Column(name = "created_at", nullable = false, updatable = false)
    @Schema(description = "일시")
    @CreatedDate
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreated() { this.createdAt = LocalDateTime.now(); }

}
