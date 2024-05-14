package b172.challenging.announcements;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import io.swagger.v3.oas.annotations.media.Schema;

import b172.challenging.common.domain.BaseTimeEntity;
import b172.challenging.common.domain.UseYn;
import b172.challenging.member.domain.Member;

@Entity
@Getter
@Table(name = "announcements")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "공지 사항")
public class Announcements extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "announcements_id", nullable = false)
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

	@Column(name = "use_yn", nullable = false)
	@Schema(description = "사용 여부")
	@Enumerated(EnumType.STRING)
	private UseYn useYn;

	public void setContent(Member member, AnnouncementsRequestDto requestDto) {
		this.registerId = member;
		this.title = requestDto.title();
		this.content = requestDto.content();
		this.useYn = requestDto.useYn();
	}
}
