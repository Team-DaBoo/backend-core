package b172.challenging.gathering.service;

import b172.challenging.gathering.dto.GatheringDto;
import b172.challenging.gathering.dto.GatheringMemberDto;
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

    public GatheringPageResponseDto findGathering(GatheringStatus gatheringStatus, AppTechPlatform platform, Pageable page) {
        Page<Gathering> gatheringPage;
        if (platform == null) {
            gatheringPage = gatheringStatus.equals(GatheringStatus.PENDING)
                    ? gatheringRepository.findByStatus(GatheringStatus.PENDING, page)
                    : gatheringRepository.findByStatusNot(GatheringStatus.PENDING, page);
        } else {
            gatheringPage = gatheringStatus.equals(GatheringStatus.PENDING)
                    ? gatheringRepository.findByPlatformAndStatus(platform, GatheringStatus.PENDING, page)
                    : gatheringRepository.findByPlatformAndStatusNot(platform, GatheringStatus.PENDING, page);
        }
        return getGatheringPageResponseDto(page, gatheringPage);
    }

    public GatheringPageResponseDto findMyGathering(Long memberId, GatheringMemberStatus gatheringMemberStatus, String made, Pageable page) {
        Page<Gathering> gatheringMyPage;
        if(made == null) {
            gatheringMyPage = gatheringMemberStatus.equals(GatheringMemberStatus.ONGOING)
                    ? gatheringRepository.findByGatheringMembersMember_IdAndGatheringMembersStatus(memberId,GatheringMemberStatus.ONGOING, page)
                    : gatheringRepository.findByGatheringMembersMember_IdAndGatheringMembersStatusNot(memberId,GatheringMemberStatus.ONGOING, page);
        } else if (made.equals("me")){
            gatheringMyPage = gatheringMemberStatus.equals(GatheringMemberStatus.ONGOING)
                    ? gatheringRepository.findByOwnerMember_IdAndGatheringMembersStatus(memberId,GatheringMemberStatus.ONGOING, page)
                    : gatheringRepository.findByOwnerMember_IdAndGatheringMembersStatusNot(memberId,GatheringMemberStatus.ONGOING, page);
        } else {
            throw new CustomRuntimeException(Exceptions.INVALID_REQUEST);
        }
        return getGatheringPageResponseDto(page, gatheringMyPage);
    }

    private GatheringPageResponseDto getGatheringPageResponseDto(Pageable page, Page<Gathering> gatheringPage) {
        List<Gathering> gatherings = new ArrayList<>(gatheringPage.getContent());

        if (gatherings.isEmpty()) {
            throw new CustomRuntimeException(Exceptions.NOT_FOUND_MEMBER);
        }

        List<GatheringDto> gatheringPageDto = gatherings.stream().map(GatheringDto::new).toList();

        return GatheringPageResponseDto.builder()
                .gatheringPages(gatheringPageDto)
                .pageNo(page.getPageNumber())
                .pageSize(page.getPageSize())
                .totalElements(gatheringPage.getTotalElements())
                .totalPages(gatheringPage.getTotalPages())
                .lastPage(gatheringPage.isLast())
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
        Gathering gathering = Gathering.builder()
                .ownerMember(member)
                .platform(gatheringMakeRequestDto.appTechPlatform())
                .gatheringImageUrl(gatheringMakeRequestDto.gatheringImageUrl())
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
                .gatheringImage(gathering.getGatheringImageUrl())
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
                .gatheringImage(gathering.getGatheringImageUrl())
                .startDate(gathering.getStartDate())
                .endDate(gathering.getEndDate())
                .workingDays(gathering.getWorkingDays())
                .goalAmount(gathering.getGoalAmount())
                .gatheringMembers(gatheringMemberList)
                .build();
    }

    @Transactional
    public GatheringMemberDto joinGathering(Long memberId, Long gatheringId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomRuntimeException(Exceptions.NOT_FOUND_MEMBER));
        Gathering gathering = gatheringRepository.findById(gatheringId)
                .orElseThrow(() -> new CustomRuntimeException(Exceptions.NOT_FOUND_GATHERING));
        GatheringMember gatheringMember = gatheringMemberRepository.findByMember(member)
                .orElse(GatheringMember.builder()
                        .member(member)
                        .gathering(gathering)
                        .amount(0L)
                        .count(0)
                        .build()
                );
        gatheringMember.setStatus(GatheringMemberStatus.ONGOING);
        gathering.addGatheringMember(gatheringMember);



        gatheringRepository.save(gathering);

        return new GatheringMemberDto(gatheringMember);
    }

    @Transactional
    public GatheringMemberDto leftGathering(Long MemberId, Long gatheringMemberId) {
        GatheringMember gatheringMember = gatheringMemberRepository.findByIdAndMemberId(gatheringMemberId, MemberId).orElseThrow(() -> new CustomRuntimeException(Exceptions.NOT_FOUND_MEMBER));
        Gathering gathering = gatheringRepository.findById(gatheringMember.getId())
                        .orElseThrow(() -> new CustomRuntimeException(Exceptions.NOT_FOUND_MEMBER));
        gatheringMember.setStatus(GatheringMemberStatus.PARTIALLY_LEFT);
        gathering.leftGatheringMember(gatheringMember);

        return new GatheringMemberDto(gatheringMember);
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
