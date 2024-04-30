package b172.challenging.gathering.service;

import b172.challenging.common.exception.CustomRuntimeException;
import b172.challenging.common.exception.Exceptions;
import b172.challenging.gathering.domain.Gathering;
import b172.challenging.gathering.domain.GatheringMember;
import b172.challenging.gathering.domain.GatheringSavingLog;
import b172.challenging.gathering.dto.response.GatheringMemberResponseDto;
import b172.challenging.gathering.dto.response.GatheringSavingLogResponseDto;
import b172.challenging.gathering.repository.GatheringMemberRepository;
import b172.challenging.gathering.repository.GatheringRepository;
import b172.challenging.gathering.repository.GatheringSavingLogRepository;
import b172.challenging.member.dto.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GatheringMemberService {
    private final GatheringRepository gatheringRepository;
    private final GatheringMemberRepository gatheringMemberRepository;
    private final GatheringSavingLogRepository gatheringSavingLogRepository;

    public List<GatheringMemberResponseDto> findGatheringId(Long gatheringId) {
        Gathering gathering = gatheringRepository.findById(gatheringId)
                .orElseThrow(() -> new CustomRuntimeException(Exceptions.NOT_FOUND_GATHERING));

        if (gathering.getGatheringMembers().isEmpty()) {
            throw new CustomRuntimeException(Exceptions.NOT_FOUND_GATHERING_MEMBER);
        }

        return gathering.getGatheringMembers().stream().map(GatheringMemberResponseDto::from).toList();
    }

    public GatheringMemberResponseDto findSavingLog(Long gatheringId, Long memberId, Pageable pageable) {

        GatheringMember gatheringMember = gatheringMemberRepository.findByGatheringIdAndMemberId(gatheringId, memberId)
                .orElseThrow(() -> new CustomRuntimeException(Exceptions.NOT_FOUND_GATHERING_MEMBER));

        Page<GatheringSavingLog> gatheringSavingLogs = gatheringSavingLogRepository.findByGatheringMember(gatheringMember, pageable);

        if (gatheringSavingLogs.isEmpty()) {
            throw new CustomRuntimeException(Exceptions.NOT_FOUND_GATHERING_SAVING_LOG);
        }

        return new GatheringMemberResponseDto(
                gatheringMember.getId(),
                MemberResponseDto.from(gatheringMember.getMember()),
                gatheringMember.getIsActive(),
                gatheringMember.getAmount(),
                gatheringMember.getCount(),
                gatheringMember.getCreatedAt(),
                gatheringMember.getUpdatedAt(),
                gatheringSavingLogs.getContent().stream().map(GatheringSavingLogResponseDto::from).toList(),
                gatheringSavingLogs.getTotalElements()
        );
    }
}
