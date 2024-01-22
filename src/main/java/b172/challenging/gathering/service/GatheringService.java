package b172.challenging.gathering.service;

import b172.challenging.gathering.dto.response.OngoingGatheringResponseDto;
import b172.challenging.gathering.dto.response.PendingGatheringResponseDto;
import b172.challenging.gathering.repository.GatheringImageRepository;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class GatheringService {
    private final GatheringRepository gatheringRepository;
    private final GatheringMemberRepository gatheringMemberRepository;
    private final GatheringMemberCustomRepository gatheringMemberCustomRepository;
    private final GatheringImageRepository gatheringImageRepository;
    private final MemberRepository memberRepository;

    public GatheringPageResponseDto findGatheringByPlatform (AppTechPlatform platform, GatheringStatus status, Pageable page){
        Page<Gathering> gatheringsPage =
                status.equals(GatheringStatus.PENDING)
                ?  gatheringRepository.findByPlatformAndStatus(platform, GatheringStatus.PENDING, page)
                :  gatheringRepository.findByPlatformAndStatusNot(platform, GatheringStatus.PENDING, page);


        List<Gathering> gatherings  = new ArrayList<>(gatheringsPage.getContent());

        return GatheringPageResponseDto.builder()
                .gatherings(gatherings)
                .pageNo(page.getPageNumber())
                .pageSize(page.getPageSize())
                .totalElements(gatheringsPage.getTotalElements())
                .totalPages(gatheringsPage.getTotalPages())
                .last(gatheringsPage.isLast())
                .build();
    }

    public GatheringPageResponseDto findAllGathering(GatheringStatus status, Pageable page) {
        Page<Gathering> gatheringsPage =
                status.equals(GatheringStatus.PENDING)
                ? gatheringRepository.findByStatus(GatheringStatus.PENDING, page)
                : gatheringRepository.findByStatusNot(GatheringStatus.PENDING, page);
        List<Gathering> gatherings  = new ArrayList<>(gatheringsPage.getContent());


        return GatheringPageResponseDto.builder()
                .gatherings(gatherings)
                .pageNo(page.getPageNumber())
                .pageSize(page.getPageSize())
                .totalElements(gatheringsPage.getTotalElements())
                .totalPages(gatheringsPage.getTotalPages())
                .last(gatheringsPage.isLast())
                .build();
    }

    public GatheringMemberPageResponseDto findMyGatheringInProgress(Long MemberId, Pageable page) {
        Page<GatheringMember> gatheringMembersPage = gatheringMemberRepository.findByMemberIdAndStatus(MemberId, GatheringMemberStatus.ONGOING, page);
        List<GatheringMember> gatheringMembers  = new ArrayList<>(gatheringMembersPage.getContent());
        return GatheringMemberPageResponseDto.builder()
                .gatheringMembers(gatheringMembers)
                .pageNo(page.getPageNumber())
                .pageSize(page.getPageSize())
                .totalElements(gatheringMembersPage.getTotalElements())
                .totalPages(gatheringMembersPage.getTotalPages())
                .last(gatheringMembersPage.isLast())
                .build();
    }

    private GatheringImage generateGatheringImage() {
        List<GatheringImage> allImages = gatheringImageRepository.findAll();
        if (allImages == null || allImages.isEmpty()) {
            throw new CustomRuntimeException(Exceptions.NOT_FOUND_IMAGE);
        }

        Random random = new Random();
        int randomIndex = random.nextInt(allImages.size());

        return allImages.get(randomIndex);
    }


    @Transactional
    public GatheringMakeResponseDto makeGathering(Long memberId, GatheringMakeRequestDto gatheringMakeRequestDto) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomRuntimeException(Exceptions.NOT_FOUND_MEMBER));
        GatheringImage gatheringImage = generateGatheringImage();
        Gathering gathering = Gathering.builder()
                 .ownerMember(member)
                .platform(gatheringMakeRequestDto.appTechPlatform())
                .gatheringImage(gatheringImage)
                .title(gatheringMakeRequestDto.title())
                .description(gatheringMakeRequestDto.description())
                .peopleNum(gatheringMakeRequestDto.peopleNum())
                .workingDays(gatheringMakeRequestDto.workingDays())
                .goalAmount(gatheringMakeRequestDto.goalAmount())
                .status(GatheringStatus.PENDING)
                .startDate(gatheringMakeRequestDto.startDate())
                .gatheringMembers(new ArrayList<>())
                .endDate(gatheringMakeRequestDto.endDate())
                .build();

        GatheringMember gatheringMember = GatheringMember.builder()
                .member(member)
                .gathering(gathering)
                .status(GatheringMemberStatus.ONGOING)
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
        Gathering gathering = gatheringRepository.findById(gatheringId)
                .orElseThrow(() -> new CustomRuntimeException(Exceptions.NOT_FOUND_GATHERING));

        List<GatheringMember> gatheringMemberList = gatheringMemberRepository.findByGatheringAndStatus(gathering, GatheringMemberStatus.ONGOING);

        boolean isJoined = gatheringMemberList.stream().anyMatch(gm -> gm.getMember().getId().equals(MemberId));

        return PendingGatheringResponseDto.builder()
                .title(gathering.getTitle())
                .description(gathering.getDescription())
                .gatheringStatus(gathering.getStatus())
                .remainNum(gathering.getPeopleNum() - gatheringMemberList.size())
                .appTechPlatform(gathering.getPlatform())
                .gatheringImage(GatheringImageResponseDto.of(gathering.getGatheringImage()).url())
                .startDate(gathering.getStartDate())
                .endDate(gathering.getEndDate())
                .workingDays(gathering.getWorkingDays())
                .goalAmount(gathering.getGoalAmount())
                .isJoined(isJoined)
                .build();
    }

    public OngoingGatheringResponseDto findOngoingGathering(Long gatheringId) {
        Gathering gathering = gatheringRepository.findById(gatheringId)
                .orElseThrow(() -> new CustomRuntimeException(Exceptions.NOT_FOUND_GATHERING));

        List<GatheringMember> gatheringMemberList = gatheringMemberRepository.findByGatheringAndStatus(gathering, GatheringMemberStatus.ONGOING);


        return OngoingGatheringResponseDto.builder()
                .title(gathering.getTitle())
                .description(gathering.getDescription())
                .appTechPlatform(gathering.getPlatform())
                .gatheringImage(GatheringImageResponseDto.of(gathering.getGatheringImage()).url())
                .startDate(gathering.getStartDate())
                .endDate(gathering.getEndDate())
                .workingDays(gathering.getWorkingDays())
                .goalAmount(gathering.getGoalAmount())
                .gatheringMembers(gatheringMemberList)
                .build();
    }

    public JoinGatheringResponseDto joinGathering(Long memberId, Long gatheringId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomRuntimeException(Exceptions.NOT_FOUND_MEMBER));
        Gathering gathering = gatheringRepository.findById(gatheringId)
                .orElseThrow(() -> new CustomRuntimeException(Exceptions.NOT_FOUND_GATHERING));
        GatheringMember gatheringMember = GatheringMember.builder()
                .member(member)
                .gathering(gathering)
                .status(GatheringMemberStatus.ONGOING)
                .amount(0L)
                .count(0).build();
        GatheringMember savedGatheringMember = gatheringMemberRepository.save(gatheringMember);


        return JoinGatheringResponseDto.builder()
                .gatheringMember(savedGatheringMember)
                .build();
    }

    @Transactional
    public LeftGatheringResponseDto leftGathering(Long MemberId, Long gatheringMemberId) {
        GatheringMember gatheringMember = gatheringMemberRepository.findByIdAndMemberId(gatheringMemberId, MemberId)
                .orElseThrow(() -> new CustomRuntimeException(Exceptions.NOT_FOUND_GATHERING_MEMBER));

        gatheringMember.setStatus(GatheringMemberStatus.PARTIALLY_LEFT);

        return LeftGatheringResponseDto.builder()
                .gatheringMember(gatheringMember)
                .build();
    }

    public Double getAchievementRate(Member member) {

        Integer totalSaving = gatheringMemberCustomRepository.gatheringMemberCountSum(member);
        Integer totalWorkingDays = gatheringMemberCustomRepository.gatheringMemberWorkingDaysSum(member);
        if (totalSaving != null && totalWorkingDays != null && totalWorkingDays != 0) {
            return (double) totalSaving / totalWorkingDays * 100;
        }
        return 0.0;
    }

    public GatheringStatisticsResponseDto gatheringStatistics(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() ->  new CustomRuntimeException(Exceptions.NOT_FOUND_MEMBER));

        Long onGoingCount = gatheringMemberRepository.countByMemberIdAndStatus(memberId, GatheringMemberStatus.ONGOING);
        Long completedCount = gatheringMemberRepository.countByMemberIdAndStatus(memberId, GatheringMemberStatus.COMPLETED);
        Long ownerGatheringCount = gatheringRepository.countByOwnerMember(member);
        Double achievementRate = getAchievementRate(member);

        return GatheringStatisticsResponseDto.builder()
                .onGoingCount(onGoingCount)
                .completedCount(completedCount)
                .ownerGatheringCount(ownerGatheringCount)
                .achievementRate(achievementRate)
                .build();
    }
}
