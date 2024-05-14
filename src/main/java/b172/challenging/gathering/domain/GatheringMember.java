package b172.challenging.gathering.domain;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import org.hibernate.annotations.BatchSize;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import io.swagger.v3.oas.annotations.media.Schema;

import b172.challenging.common.domain.BaseTimeEntity;
import b172.challenging.member.domain.Member;

@Entity
@Getter
@Table(name = "gathering_member")
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Schema(description = "모임 가입한 사용자")
public class GatheringMember extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "gathering_member_id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "group_id", nullable = false)
	@Schema(description = "모임 ID")
	private Gathering gathering;

	@ManyToOne
	@JoinColumn(name = "member_id", nullable = false)
	@Schema(description = "가입한 사용자 ID")
	private Member member;

	@Column(nullable = false, columnDefinition = "boolean default true")
	@Schema(description = "참여 중")
	private Boolean isActive;

	@Column(nullable = false, columnDefinition = "bigint default 0")
	@Schema(description = "모은 금액")
	private Long amount;

	@Column(nullable = false, columnDefinition = "bigint default 0")
	@Schema(description = "모은 횟수")
	private int count;

	@BatchSize(size = 10)
	@OneToMany(mappedBy = "gatheringMember", cascade = CascadeType.ALL)
	private List<GatheringSavingLog> gatheringSavingLogs;

	public void setGathering(Gathering gathering) {
		this.gathering = gathering;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public void addSavingLog(GatheringSavingLog gatheringSavingLog) {
		gatheringSavingLogs.add(gatheringSavingLog);
		gatheringSavingLog.setGatheringMember(this);
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}
}
