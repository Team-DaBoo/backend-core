package b172.challenging.gathering.service;

import b172.challenging.common.exception.CustomRuntimeException;
import b172.challenging.common.exception.Exceptions;
import b172.challenging.gathering.domain.GatheringMember;
import b172.challenging.gathering.dto.response.GatheringMemberResponseDto;
import b172.challenging.gathering.dto.response.GatheringSavingLogResponseDto;
import b172.challenging.gathering.repository.GatheringMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GatheringMemberService {
    private final GatheringMemberRepository gatheringMemberRepository;

    public List<GatheringMemberResponseDto> findGatheringId(Long gatheringId) {

        List<GatheringMember> gatheringMember = gatheringMemberRepository.findByGatheringId(gatheringId);

        if (gatheringMember.isEmpty()) {
            throw new CustomRuntimeException(Exceptions.NOT_FOUND_GATHERING_MEMBER);
        }

        return gatheringMember.stream().map(GatheringMemberResponseDto::from).toList();
    }

//    public GatheringSavingLogResponseDto findGatheringMember(Long gatheringId, Long memberId) {

//        return GatheringSavingLogResponseDto.builder()
//                .gatheringMemberList(findGatheringId(gatheringId))
//                .build();
//    }

}
