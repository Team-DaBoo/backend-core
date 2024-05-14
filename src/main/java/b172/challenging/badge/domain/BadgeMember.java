package b172.challenging.badge.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import io.swagger.v3.oas.annotations.media.Schema;

import b172.challenging.common.domain.BaseTimeEntity;
import b172.challenging.member.domain.Member;

@Entity
@Getter
@Table(name = "badge_member")
@NoArgsConstructor
@Schema(description = "사용자 보유 배지 정보")
public class BadgeMember extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "badge_member_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false)
	@Schema(description = "배지 보유 사용자 ID")
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "badge_id", nullable = false)
	@Schema(description = "배지 ID")
	private Badge badge;

	@Builder
	public BadgeMember(Long id, Badge badge, Member member) {
		this.id = id;
		this.badge = badge;
		this.member = member;
	}
}
