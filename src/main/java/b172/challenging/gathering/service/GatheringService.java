package b172.challenging.gathering.service;

import b172.challenging.admin.dto.GatheringSearchRequestDto;
import b172.challenging.common.dto.PageResponse;
import b172.challenging.gathering.dto.response.GatheringResponseDto;
import b172.challenging.gathering.dto.response.OngoingGatheringResponseDto;
import b172.challenging.gathering.dto.response.PendingGatheringResponseDto;
import b172.challenging.gathering.repository.GatheringMemberCustomRepository;
import b172.challenging.member.domain.Member;
import b172.challenging.member.repository.MemberRepository;
import b172.challenging.common.exception.CustomRuntimeException;
import b172.challenging.common.exception.Exceptions;
import b172.challenging.gathering.domain.*;
import b172.challenging.gathering.dto.request.GatheringMakeRequestDto;
import b172.challenging.gathering.dto.response.*;
import b172.challenging.gathering.repository.GatheringMemberRepository;
import b172.challenging.gathering.repository.GatheringRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor
public class GatheringService {
    private final GatheringRepository gatheringRepository;
    private final GatheringMemberRepository gatheringMemberRepository;
    private final GatheringMemberCustomRepository gatheringMemberCustomRepository;
    private final MemberRepository memberRepository;

    public PageResponse<GatheringResponseDto> findAllGathering(GatheringSearchRequestDto gatheringSearchRequestDto, Pageable pageable) {
        return PageResponse.from(gatheringRepository.searchByCriteria(gatheringSearchRequestDto,pageable).map(GatheringResponseDto::from));
    }

    public PageResponse<GatheringResponseDto> findGathering(GatheringStatus gatheringStatus, AppTechPlatform platform, Pageable page) {
        Page<Gathering> gatheringPage;
        if(platform == null && gatheringStatus == null){
            gatheringPage = gatheringRepository.findAll(page);
        } else if (platform == null) {
            gatheringPage = gatheringStatus.equals(GatheringStatus.PENDING)
                    ? gatheringRepository.findByStatus(GatheringStatus.PENDING, page)
                    : gatheringRepository.findByStatusNot(GatheringStatus.PENDING, page);
        } else if(gatheringStatus == null){
            gatheringPage = gatheringRepository.findByPlatform(platform, page);
        } else {
            gatheringPage = gatheringStatus.equals(GatheringStatus.PENDING)
                    ? gatheringRepository.findByPlatformAndStatus(platform, GatheringStatus.PENDING, page)
                    : gatheringRepository.findByPlatformAndStatusNot(platform, GatheringStatus.PENDING, page);
        }
        return PageResponse.from(gatheringPage.map(GatheringResponseDto::from));
    }

    public PageResponse<GatheringResponseDto> findMyGathering(Long memberId, Boolean isActive, String made, Pageable page) {
        Page<Gathering> gatheringMyPage = made == null
                ? gatheringRepository.findByGatheringMembersMember_IdAndGatheringMembers_IsActive(memberId,isActive, page)
                : gatheringRepository.findByOwnerMember_IdAndGatheringMembers_IsActive(memberId,isActive, page);
        return PageResponse.from(gatheringMyPage.map(GatheringResponseDto::from));
    }

    @Transactional
    public GatheringMakeResponseDto makeGathering(Long memberId, GatheringMakeRequestDto gatheringMakeRequestDto) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomRuntimeException(Exceptions.NOT_FOUND_MEMBER));
        Gathering gathering = Gathering.builder()
                .ownerMember(member)
                .platform(gatheringMakeRequestDto.appTechPlatform())
                .gatheringImageUrl(gatheringMakeRequestDto.gatheringImageUrl() == null ? "" : gatheringMakeRequestDto.gatheringImageUrl())
                .title(gatheringMakeRequestDto.title())
                .description(gatheringMakeRequestDto.description())
                .peopleNum(gatheringMakeRequestDto.peopleNum())
                .workingDays(gatheringMakeRequestDto.workingDays())
                .goalAmount(gatheringMakeRequestDto.goalAmount())
                .status(GatheringStatus.PENDING)
                .startDate(gatheringMakeRequestDto.startDate() == null ? LocalDateTime.now() : gatheringMakeRequestDto.startDate())
                .endDate(gatheringMakeRequestDto.endDate() == null ? LocalDateTime.now().plusDays(gatheringMakeRequestDto.workingDays()) : gatheringMakeRequestDto.endDate())
                .gatheringMembers(new ArrayList<>())
                .build();

        GatheringMember gatheringMember = GatheringMember.builder()
                .member(member)
                .gathering(gathering)
                .isActive(true)
                .amount(0L)
                .count(0)
                .build();

        gathering.addGatheringMember(gatheringMember);

        gatheringRepository.save(gathering);

        return GatheringMakeResponseDto.builder()
                .id(gathering.getId())
                .title(gathering.getTitle())
                .owner(gathering.getOwnerMember())
                .build();
    }

    public PendingGatheringResponseDto findPendingGathering(Long gatheringId, Long MemberId) {
        Gathering gathering = gatheringRepository.findByIdAndStatus(gatheringId, GatheringStatus.PENDING)
                .orElseThrow(() -> new CustomRuntimeException(Exceptions.NOT_FOUND_GATHERING));

        List<GatheringMember> gatheringMemberList = gatheringMemberRepository.findByGathering(gathering);

        boolean isJoined = gatheringMemberList.stream().anyMatch(gm -> gm.getMember().getId().equals(MemberId));

        return PendingGatheringResponseDto.builder()
                .title(gathering.getTitle())
                .description(gathering.getDescription())
                .gatheringStatus(gathering.getStatus())
                .remainNum(gathering.getPeopleNum() - gatheringMemberList.size())
                .appTechPlatform(gathering.getPlatform())
                .gatheringImage(gathering.getGatheringImageUrl())
                .startDate(gathering.getStartDate())
                .endDate(gathering.getEndDate())
                .workingDays(gathering.getWorkingDays())
                .goalAmount(gathering.getGoalAmount())
                .isJoined(isJoined)
                .build();
    }

    public OngoingGatheringResponseDto findOngoingGathering(Long gatheringId) {
        Gathering gathering = gatheringRepository.findByIdAndStatus(gatheringId, GatheringStatus.ONGOING)
                .orElseThrow(() -> new CustomRuntimeException(Exceptions.NOT_FOUND_GATHERING));

        List<GatheringMember> gatheringMemberList = gatheringMemberRepository.findByGathering(gathering);


        return OngoingGatheringResponseDto.builder()
                .title(gathering.getTitle())
                .description(gathering.getDescription())
                .appTechPlatform(gathering.getPlatform())
                .gatheringImage(gathering.getGatheringImageUrl())
                .startDate(gathering.getStartDate())
                .endDate(gathering.getEndDate())
                .workingDays(gathering.getWorkingDays())
                .goalAmount(gathering.getGoalAmount())
                .gatheringMembers(gatheringMemberList)
                .build();
    }

    @Transactional
    public GatheringMemberResponseDto joinGathering(Long memberId, Long gatheringId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomRuntimeException(Exceptions.NOT_FOUND_MEMBER));
        Gathering gathering = gatheringRepository.findById(gatheringId)
                .orElseThrow(() -> new CustomRuntimeException(Exceptions.NOT_FOUND_GATHERING));
        GatheringMember gatheringMember = GatheringMember.builder()
                .member(member)
                .gathering(gathering)
                .amount(0L)
                .count(0)
                .build();
        gatheringMember.setIsActive(false);
        gathering.addGatheringMember(gatheringMember);

        gatheringRepository.save(gathering);

        return GatheringMemberResponseDto.from(gatheringMember);
    }

    @Transactional
    public GatheringMemberResponseDto leftGathering(Long MemberId, Long gatheringMemberId) {
        GatheringMember gatheringMember = gatheringMemberRepository.findByIdAndMemberId(gatheringMemberId, MemberId).orElseThrow(() -> new CustomRuntimeException(Exceptions.NOT_FOUND_GATHERING_MEMBER));
        Gathering gathering = gatheringMember.getGathering();
        gathering.leftGatheringMember(gatheringMember);

        return GatheringMemberResponseDto.from(gatheringMember);
    }

    public Double getAchievementRate(Member member) {

        Integer totalSaving = gatheringMemberCustomRepository.gatheringMemberCountSum(member);
        Integer totalWorkingDays = gatheringMemberCustomRepository.gatheringMemberWorkingDaysSum(member);
        if (totalSaving != null && totalWorkingDays != null && totalWorkingDays != 0) {
            return (double) totalSaving / totalWorkingDays * 100;
        }
        return (double) 0.0;
    }

    public GatheringStatisticsResponseDto gatheringStatistics(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() ->  new CustomRuntimeException(Exceptions.NOT_FOUND_MEMBER));

        Long pendingCount = gatheringMemberRepository.countByMemberIdAndGathering_Status(memberId, GatheringStatus.PENDING);
        Long onGoingCount = gatheringMemberRepository.countByMemberIdAndGathering_Status(memberId, GatheringStatus.ONGOING);
        Long completedCount = gatheringMemberRepository.countByMemberIdAndGathering_Status(memberId, GatheringStatus.COMPLETED);
        Long ownerGatheringCount = gatheringRepository.countByOwnerMember(member);
        Double achievementRate = getAchievementRate(member);

        return GatheringStatisticsResponseDto.builder()
                .pendingCount(pendingCount)
                .onGoingCount(onGoingCount)
                .completedCount(completedCount)
                .ownerGatheringCount(ownerGatheringCount)
                .achievementRate(achievementRate)
                .build();
    }

    public GatheringResponseDto findById(Long gatheringId) {
        Gathering gathering = gatheringRepository.findById(gatheringId)
                .orElseThrow(() -> new CustomRuntimeException(Exceptions.NOT_FOUND_GATHERING));
        return GatheringResponseDto.from(gathering);
    }
    @Transactional
    public void deleteGathering(Long gatheringId) {
        Gathering gathering = gatheringRepository.findById(gatheringId)
                .orElseThrow(() -> new CustomRuntimeException(Exceptions.NOT_FOUND_GATHERING));
        gatheringRepository.delete(gathering);
    }
}
