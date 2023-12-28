package b172.challenging.gathering.service;

import b172.challenging.auth.domain.Member;
import b172.challenging.auth.repository.MemberRepository;
import b172.challenging.common.exception.CustomRuntimeException;
import b172.challenging.common.exception.ErrorCode;
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
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GatheringService {
    private final GatheringRepository gatheringRepository;
    private final GatheringMemberRepository gatheringMemberRepository;
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

    public GatheringMemberPageResponseDto findMyGatheringInProgress(Long userId, Pageable page) {
        Page<GatheringMember> gatheringMembersPage = gatheringMemberRepository.findByMemberIdAndStatus(userId, GatheringMemberStatus.ONGOING, page);
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

    @Transactional
    public GatheringMakeResponseDto makeGathering(Long memberId, GatheringMakeRequestDto gatheringMakeRequestDto) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.NOT_FOUND_MEMBER));

        Gathering gathering = Gathering.builder()
                 .ownerMember(member)
                .platform(gatheringMakeRequestDto.appTechPlatform())
                .title(gatheringMakeRequestDto.title())
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

    public GatheringStatusResponseDto findGatheringStatus(Long gatheringId, Long userId) {
        Optional<Gathering> gatheringOptional = gatheringRepository.findById(gatheringId);
        Gathering gathering = gatheringOptional.orElseThrow(() -> new CustomRuntimeException(ErrorCode.NOT_FOUND_MEMBER));

        List<GatheringMember> gatheringMemberList = gatheringMemberRepository.findByGatheringAndStatus(gathering, GatheringMemberStatus.ONGOING);

        boolean isExist = gatheringMemberList.stream().anyMatch(gm -> gm.getMember().getId().equals(userId));

        return GatheringStatusResponseDto.builder()
                .title(gathering.getTitle())
                .gatheringStatus(gathering.getStatus())
                .remainNum(gathering.getPeopleNum() - gatheringMemberList.size())
                .appTechPlatform(gathering.getPlatform())
                .workingDays(gathering.getWorkingDays())
                .goalAmount(gathering.getGoalAmount())
                .isExist(isExist)
                .build();
    }

    public JoinGatheringResponseDto joinGathering(Long userId, Long gatheringId) {
        Member member = new Member(userId);
        Gathering gathering = new Gathering(gatheringId);
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
    public LeftGatheringResponseDto leftGathering(Long userId, Long gatheringMemberId) {
        GatheringMember gatheringMember = gatheringMemberRepository.findByIdAndMemberId(gatheringMemberId, userId).orElseThrow(() -> new CustomRuntimeException(ErrorCode.NOT_FOUND_MEMBER));

        gatheringMember.setStatus(GatheringMemberStatus.PARTIALLY_LEFT);

        gatheringMemberRepository.save(gatheringMember);

        return LeftGatheringResponseDto.builder()
                .gatheringMember(gatheringMember)
                .build();
    }

    public GatheringStatusCountResponseDto gatheringStatusCount(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() ->  new CustomRuntimeException(ErrorCode.NOT_FOUND_MEMBER));

        Long onGoingCount = gatheringMemberRepository.countByMemberIdAndStatus(memberId, GatheringMemberStatus.ONGOING);
        Long completedCount = gatheringMemberRepository.countByMemberIdAndStatus(memberId, GatheringMemberStatus.COMPLETED);
        Long ownerGatheringCount = gatheringRepository.countByOwnerMember(member);

        return GatheringStatusCountResponseDto.builder()
                .onGoingCount(onGoingCount)
                .completedCount(completedCount)
                .ownerGatheringCount(ownerGatheringCount)
                .build();
    }
}
