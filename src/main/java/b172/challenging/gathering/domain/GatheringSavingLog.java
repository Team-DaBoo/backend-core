package b172.challenging.gathering.domain;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PreRemove;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "gathering_saving_log")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GatheringSavingLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "gathering_saving_log_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "gathering_memeber_id", nullable = false)
	private GatheringMember gatheringMember;

	@Column(nullable = false, columnDefinition = "bigint default 0")
	private Long amount;

	@Column(name = "certificated_at", nullable = false)
	private LocalDateTime certificatedAt;

	@OneToMany(mappedBy = "gatheringSavingLog", cascade = CascadeType.ALL)
	private List<GatheringSavingCertification> gatheringSavingCertifications;

	public void setAmount(Long amount) {
		gatheringMember.setAmount(gatheringMember.getAmount() - this.amount + amount);
		this.amount = amount;
	}

	@PreRemove
	public void removeGatheringMemberAmount() {
		gatheringMember.setAmount(
			gatheringMember.getAmount() - this.amount < 0 ? 0 : gatheringMember.getAmount() - this.amount);
	}

	public void setGatheringMember(GatheringMember gatheringMember) {
		this.gatheringMember = gatheringMember;
	}
}
