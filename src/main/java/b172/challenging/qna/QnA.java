package b172.challenging.qna;

import b172.challenging.announcements.AnnouncementsRequestDto;
import b172.challenging.common.domain.BaseTimeEntity;
import b172.challenging.common.domain.UseYn;
import b172.challenging.member.domain.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "qna")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Q&A")
public class QnA extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "qna_id", nullable = false)
    private long id;

    @Column(nullable = false)
    @Schema(description = "제목")
    private String title;

    @Column
    @Schema(description = "내용")
    @Lob
    private String content;

    @Schema(description = "등록한 사람")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "register_id", nullable = false)
    private Member registerId;

    @Column(name = "use_yn" ,nullable = false)
    @Schema(description = "사용 여부")
    @Enumerated(EnumType.STRING)
    private UseYn useYn;

    public void setContent(Member member, QnARequestDto requestDto){
        this.registerId = member;
        this.title = requestDto.title();
        this.content = requestDto.content();
        this.useYn = requestDto.useYn();
    }
}
