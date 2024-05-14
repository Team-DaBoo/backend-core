//package b172.challenging.gathering.querydsl;
//
//import b172.challenging.auth.domain.Member;
//import b172.challenging.common.config.QueryDslConfig;
//import b172.challenging.gathering.domain.AppTechPlatform;
//import b172.challenging.gathering.domain.Gathering;
//import b172.challenging.gathering.domain.GatheringStatus;
//import b172.challenging.gathering.domain.QGathering;
//import b172.challenging.gathering.repository.GatheringRepository;
//import b172.challenging.gathering.service.GatheringService;
//import org.junit.jupiter.api.*;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Optional;
//
//import static org.mockito.Mockito.*;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//
////@ExtendWith(SpringExtension.class)
////@SpringBootTest
////@Transactional
//@ExtendWith(MockitoExtension.class)
//public class GatheringQueryDslTest {
//
//	@MockBean
//	private GatheringService gatheringService;
//
//	@MockBean
//	private GatheringRepository gatheringRepository;
//
////	@BeforeEach
////	public void beforeEach(){
////		gatheringRepository = new GatheringRepository();
////		gatheringService = new GatheringService(gatheringRepository);
////	}
////	@AfterEach
////	public void afterEach() {
////		gatheringRepository.clearStore();
////	}
//
//
//	@Test
//	@DisplayName("모으기 전체 가져오기 - 성공")
//	@Transactional
//	void saveGathering_isSuccess() {
//
//		//given
//		Member member = new Member();
//		AppTechPlatform atp = AppTechPlatform.TOSS;
//		GatheringStatus gs = GatheringStatus.PENDING;
//
//		Gathering gathering = Gathering.builder()
//				.id(12345L)
//				.ownerMember(member)
//				.platform(atp)
//				.title("제목")
//				.peopleNum(2)
//				.goalAmount(3000L)
//				.workingDays(30)
//				.status(gs)
//				.startDate(LocalDateTime.now())
//				.endDate(LocalDateTime.now())
//				.createdAt(LocalDateTime.now())
//				.updatedAt(LocalDateTime.now())
//				.build();
//
//		// Mock 설정: save 메서드가 호출될 때, 저장된 Gathering을 반환하도록 설정
//		when(gatheringRepository.save(gathering)).thenReturn(gathering);
//
//		// when
//		Gathering savedGathering = gatheringRepository.save(gathering);
//
//		// then
//		Assertions.assertEquals(gathering, savedGathering, "저장된 Gathering과 반환된 Gathering이 같아야 합니다.");
//
//		// Mock 설정: findAll 메서드가 호출될 때, 빈 Page<Gathering> 객체를 반환하도록 설정
//		when(gatheringRepository.findAll(PageRequest.of(0, 10))).thenReturn(Page.empty());
//
//		// when
////		Page<Gathering> result = gatheringService.findAllGathering(PageRequest.of(0, 10));
//
//		// then
////		Assertions.assertEquals(0, result.getTotalElements(), "데이터가 없는 경우, 빈 Page를 반환해야 합니다.");
//
//
//	}
//
//}
