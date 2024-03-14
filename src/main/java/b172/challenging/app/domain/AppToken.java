package b172.challenging.app.domain;

import b172.challenging.common.domain.BaseTimeEntity;
import b172.challenging.member.domain.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "app_token")
@NoArgsConstructor
public class AppToken extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "app_token_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "notification_agree", nullable = false)
    private boolean notificationAgree;

    @Column(nullable = false, length = 128)
    private String device;

    @Column(nullable = false, length = 128)
    private String token;

}
