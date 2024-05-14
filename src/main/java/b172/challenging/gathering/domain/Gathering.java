package b172.challenging.gathering.domain;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import io.swagger.v3.oas.annotations.media.Schema;

import b172.challenging.common.domain.BaseTimeEntity;
import b172.challenging.member.domain.Member;

@Entity
@Getter
@Table(name = "gathering",
	indexes = @Index(name = "idx_gathering_platform", columnList = "platform")
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Schema(description = "모임 정보")
public class Gathering extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "gathering_id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "owner_member_id", nullable = false)
	@Schema(description = "모임 개설자 Id")
	private Member ownerMember;

	@Column(nullable = false, length = 10)
	@Enumerated(EnumType.STRING)
	@Schema(description = "앱 플랫폼")
	private AppTechPlatform platform;

	private String gatheringImageUrl;

	@Column(nullable = false)
	@Schema(description = "모임 제목")
	private String title;

	@Schema(description = "모임 설명")
	private String description;

	@Column(name = "people_num", nullable = false)
	@Schema(description = "모집 할 인원 수")
	private int peopleNum;

	@Column(name = "participants_num", columnDefinition = "bigint default 0")
	@Schema(description = "참여 중인 인원 수")
	private int participantsNum;

	@Column(name = "goal_amount", nullable = false)
	@Schema(description = "목표 금액")
	private Long goalAmount;

	@Column(name = "working_days", nullable = false)
	@Schema(description = "기간")
	private int workingDays;

	@Column(nullable = false, length = 10)
	@Enumerated(EnumType.STRING)
	private GatheringStatus status;

	@Column(name = "start_date")
	@Schema(description = "시작 일시")
	private LocalDateTime startDate;

	@Column(name = "end_date")
	@Schema(description = "만료 일시")
	private LocalDateTime endDate;

	@OneToMany(mappedBy = "gathering", cascade = CascadeType.ALL)
	private List<GatheringMember> gatheringMembers;

	public void addGatheringMember(GatheringMember gatheringMember) {
		gatheringMembers.add(gatheringMember);
		gatheringMember.setGathering(this);
		++this.participantsNum;
		if (participantsNum == peopleNum) {
			this.status = GatheringStatus.ONGOING;
		}
	}

	public void leftGatheringMember(GatheringMember gatheringMember) {
		gatheringMembers.remove(gatheringMember);
		gatheringMember.setIsActive(false);
		this.participantsNum = this.participantsNum - 1 < 0 ? 0 : --this.participantsNum;
		gatheringMembers.add(gatheringMember);
		gatheringMember.setGathering(this);
	}
}
