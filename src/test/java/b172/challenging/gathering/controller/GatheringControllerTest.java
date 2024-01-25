package b172.challenging.gathering.controller;

import b172.challenging.auth.config.WithCustomMockUser;
import b172.challenging.auth.oauth.CustomOauth2User;
import b172.challenging.common.exception.CustomRuntimeException;
import b172.challenging.common.exception.Exceptions;
import b172.challenging.gathering.domain.*;
import b172.challenging.gathering.repository.GatheringMemberRepository;
import b172.challenging.gathering.repository.GatheringRepository;
import b172.challenging.member.domain.Member;
import b172.challenging.member.domain.OauthProvider;
import b172.challenging.member.domain.Role;
import b172.challenging.member.repository.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
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
    public void 내_참가_현황_가져오기_참가중() throws Exception {

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

        mockMvc.perform(get("/v1/gathering/my/ONGOING"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithCustomMockUser
    public void 모임_만들기_방장() throws Exception {

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

    @Test
    @WithCustomMockUser
    public void 모임_참가_하기() throws Exception {

        Member member1  = Member.builder()
                .nickname("nickname1")
                .oauthProvider(OauthProvider.GOOGLE)
                .oauthId("oauthId1")
                .build();
        member1.setRole(Role.MEMBER);

        memberRepository.save(member1);

        Gathering gathering = Gathering.builder()
                .ownerMember(member1)
                .platform(AppTechPlatform.TOSS)
                .gatheringImageUrl("https://image-url.da-boo.shop")
                .title("토스 모임 제목")
                .description("토스 모임 설명")
                .peopleNum(4)
                .participantsNum(0)
                .workingDays(30)
                .goalAmount(6000L)
                .status(GatheringStatus.PENDING)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusDays(30))
                .gatheringMembers(new ArrayList<>())
                .build();

        GatheringMember gatheringMember = GatheringMember.builder()
                .member(member1)
                .gathering(gathering)
                .status(GatheringMemberStatus.ONGOING)
                .amount(0L)
                .count(0)
                .build();

        gathering.addGatheringMember(gatheringMember);

        Gathering savedGathering = gatheringRepository.save(gathering);

        System.out.println("####################TEST START ##############");

        mockMvc.perform(put("/v1/gathering/join/"+savedGathering.getId()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithCustomMockUser
    public void 모임_나가기() throws Exception {

        CustomOauth2User oauth2User = (CustomOauth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Member member = memberRepository.findById(oauth2User.getMemberId())
                .orElseThrow(() -> new CustomRuntimeException(Exceptions.NOT_FOUND_MEMBER));


        Gathering gathering = Gathering.builder()
                .ownerMember(member)
                .platform(AppTechPlatform.TOSS)
                .gatheringImageUrl("https://image-url.da-boo.shop")
                .title("토스 모임 제목")
                .description("토스 모임 설명")
                .peopleNum(4)
                .participantsNum(0)
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

        Gathering savedGathering = gatheringRepository.save(gathering);

        System.out.println("####################TEST START ##############");

        mockMvc.perform(post("/v1/gathering/left/"+savedGathering.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gathering.title", is("토스 모임 제목")))
        ;

        Gathering resultGathering = gatheringRepository.findById(savedGathering.getId())
                .orElseThrow(() -> new CustomRuntimeException(Exceptions.NOT_FOUND_MEMBER));

        assertThat(resultGathering.getTitle()).isEqualTo("토스 모임 제목");

    }

}
