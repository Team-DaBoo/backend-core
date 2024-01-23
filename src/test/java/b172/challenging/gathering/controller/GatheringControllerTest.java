package b172.challenging.gathering.controller;

import b172.challenging.auth.config.WithCustomMockUser;
import b172.challenging.auth.oauth.CustomOauth2User;
import b172.challenging.common.exception.CustomRuntimeException;
import b172.challenging.common.exception.Exceptions;
import b172.challenging.gathering.domain.*;
import b172.challenging.gathering.repository.GatheringMemberRepository;
import b172.challenging.gathering.repository.GatheringRepository;
import b172.challenging.member.domain.Member;
import b172.challenging.member.repository.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GatheringControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    GatheringRepository gatheringRepository;

    @Autowired
    GatheringMemberRepository gatheringMemberRepository;

    @Test
    @WithCustomMockUser
    public void 내_참가_현황_가져오기() throws Exception {

        CustomOauth2User oauth2User = (CustomOauth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Member member = memberRepository.findById(oauth2User.getMemberId())
                .orElseThrow(() -> new CustomRuntimeException(Exceptions.NOT_FOUND_MEMBER));

        Gathering gathering = Gathering.builder()
                .ownerMember(member)
                .platform(AppTechPlatform.TOSS)
                .gatheringImageUrl("https://image-url.da-boo.shop")
                .title("토스 모임 제목")
                .description("토스 모임 설명")
                .peopleNum(2)
                .workingDays(30)
                .goalAmount(6000L)
                .status(GatheringStatus.PENDING)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusDays(30))
                .gatheringMembers(new ArrayList<>())
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

        mockMvc.perform(get("/v1/gathering/in-progress"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithCustomMockUser
    public void 모임_만들기() throws Exception {

        Map<String, Object> info = new HashMap<>();
        info.put("appTechPlatform","TOSS");
        info.put("title","TOSS Application");
        info.put("description","TOSS로 돈 모으기");
        info.put("peopleNum",2);
        info.put("workingDays",30);
        info.put("goalAmount",6000);
        info.put("gatheringImageUrl","https://image-url.da-boo.shop");
        info.put("startDate","20240122000000");
        info.put("endDate","20240221000000");

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(info);


        mockMvc.perform(post("/v1/gathering")
                        .contentType("application/json")
                        .content(jsonString))
                .andDo(print())
                .andExpect(status().isOk());
    }

}
