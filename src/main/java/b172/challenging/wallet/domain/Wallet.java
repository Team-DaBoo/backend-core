package b172.challenging.wallet.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import io.swagger.v3.oas.annotations.media.Schema;

import b172.challenging.common.domain.BaseTimeEntity;
import b172.challenging.member.domain.Member;
import b172.challenging.myhome.domain.MyHome;

@Entity
@Getter
@Table(name = "wallet")
@NoArgsConstructor
@Schema(description = "사용자 포인트")
@Builder
@AllArgsConstructor
public class Wallet extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "wallet_id")
	private Long id;

	@Schema(description = "Member id")
	@OneToOne
	@JoinColumn(name = "member_id")
	private Member member;

	@Schema(description = "MyHome id")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "home_id", columnDefinition = "bigint default 1") // FIXME: home 정보 추가 후 nullable=false로 변경
	private MyHome myHome;

	@Schema(description = "나의 집 이름")
	@Column(name = "home_name", length = 30)
	private String homeName;

	@Schema(description = "현재 포인트")
	@Column(nullable = false, columnDefinition = "bigint default 0")
	private Long point;

	@Schema(description = "전체 모은 돈")
	@Column(name = "save_amount", nullable = false, columnDefinition = "bigint default 0")
	private Long saveAmount;

	public void savePoint(Long amount) {
		this.saveAmount += amount;
		this.point += amount;
	}
}
