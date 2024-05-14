package b172.challenging.app.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;

import b172.challenging.common.domain.BaseTimeEntity;
import b172.challenging.member.domain.Member;

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
