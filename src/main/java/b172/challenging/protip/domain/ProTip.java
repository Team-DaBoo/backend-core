package b172.challenging.protip.domain;

import b172.challenging.common.domain.BaseTimeEntity;
import b172.challenging.member.domain.Member;
import b172.challenging.common.domain.UseYn;
import b172.challenging.protip.dto.ProTipRequestDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "pro_tip",
        indexes = {
            @Index(name = "idx_pro_tip_type", columnList ="type")
        })
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "꿀팁 정보")
public class ProTip extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pro_tip_id", nullable = false)
    private long id;

    @Column(nullable = false)
    @Schema(description = "분류")
    private ProTipType type;

    @Column(nullable = false)
    @Schema(description = "제목")
    private String title;

    @Column
    @Schema(description = "내용")
    private String content;

    @Column(name = "img_url")
    @Schema(description = "이미지 url")
    private String imgUrl;

    @Column(name = "app_link_url")
    @Schema(description = "앱 링크")
    private String appLinkUrl;

    @Schema(description = "등록한 사람")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "register_id", nullable = false)
    private Member registerId;

    @Column(name = "use_yn" ,nullable = false)
    @Schema(description = "사용 여부")
    private UseYn useYn;

    public void setContent(Member member, ProTipRequestDto requestDto){
        this.registerId = member;
        this.type = requestDto.proTipType();
        this.title = requestDto.title();
        this.content = requestDto.content();
        this.imgUrl = requestDto.imgUrl();
        this.appLinkUrl = requestDto.appLinkUrl();
        this.useYn = requestDto.useYn();
    }

}
