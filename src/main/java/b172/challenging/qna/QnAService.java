package b172.challenging.qna;

import b172.challenging.common.domain.UseYn;
import b172.challenging.common.dto.PageResponse;
import b172.challenging.common.dto.SearchRequestDto;
import b172.challenging.member.domain.Member;
import b172.challenging.member.domain.Role;
import b172.challenging.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QnAService {

    private final QnARepository qnARepository;
    private final MemberRepository memberRepository;
    public PageResponse<QnAResponseDto> findAllQnA(Role role, SearchRequestDto searchRequestDto, Pageable pageable) {
        Page<QnA> qnAPage =
                role == Role.ADMIN
                        ? qnARepository.searchByCriteria(searchRequestDto, pageable)
                        : qnARepository.findByUseYnIs(UseYn.Y, pageable);

        return PageResponse.from(qnAPage.map(QnAResponseDto::from));
    }

    public QnA findQnAById(Long id) {
        return qnARepository.findById(id).orElseThrow();
    }

    public void createQnA(Long memberId, QnARequestDto qnARequestDto) {
        Member member = memberRepository.getOrThrow(memberId);

        QnA qnA = QnA.builder()
                .title(qnARequestDto.title())
                .content(qnARequestDto.content())
                .registerId(member)
                .useYn(UseYn.Y)
                .build();
        qnARepository.save(qnA);
    }

    @Transactional
    public void updateQnA(Long id, Long memberId, QnARequestDto qnARequestDto) {
        Member member = memberRepository.getOrThrow(memberId);

        QnA qnA = qnARepository.findById(id).orElseThrow();
        qnA.setContent(member, qnARequestDto);
        qnARepository.save(qnA);
    }

    public void deleteQnA(Long id) {
        qnARepository.deleteById(id);
    }
}
