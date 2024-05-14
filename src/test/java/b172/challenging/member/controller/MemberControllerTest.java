package b172.challenging.member.controller;

import static org.hamcrest.CoreMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import jakarta.transaction.Transactional;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import b172.challenging.auth.config.WithCustomMockUser;
import b172.challenging.member.repository.MemberRepository;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MemberControllerTest {
	@Autowired
	MockMvc mockMvc;

	@Autowired
	MemberRepository memberRepository;

	@Test
	@WithCustomMockUser
	public void 내_정보_가져오기() throws Exception {
		mockMvc.perform(get("/v1/members/profile"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.nickName", is("nickname")));
	}

}
