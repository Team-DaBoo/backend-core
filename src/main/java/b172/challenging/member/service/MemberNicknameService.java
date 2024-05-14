package b172.challenging.member.service;

import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import b172.challenging.member.domain.Member;
import b172.challenging.member.repository.MemberRepository;

@Slf4j
@Service
@Getter
@AllArgsConstructor
public class MemberNicknameService {

	private static final String[] ANIMALS = {
		"사자", "호랑이", "곰", "코끼리", "기린",
		"원숭이", "펭귄", "하마", "앵무새", "돌고래",
		"햄스터", "고양이", "강아지", "사슴", "북극곰",
		"수달", "늑대", "쥐", "다람쥐", "토끼",
		"고릴라", "침팬지", "오랑우탄", "물소", "기니피그",
		"악어", "바다거북", "사막여우", "표범", "악어거북",
		"팬더", "늑대", "범고래", "백조", "오리",
		"독수리", "부엉이", "불가사리", "해마", "산양",
		"코알라", "해마", "왈라비", "낙타", "햄스터"
	};

	private static final String[] ADJECTIVES = {
		"재미있는", "귀여운", "용감한", "지혜로운", "날렵한",
		"건장한", "성숙한", "부드러운", "친절한", "당당한",
		"활발한", "창의적인", "화려한", "훌륭한", "자유로운",
		"똑똑한", "명랑한", "화목한", "단호한", "밝은",
		"신나는", "섬세한", "진중한", "꼼꼼한", "단순한",
		"착한", "근면한", "상냥한", "깔끔한", "책임있는",
		"용기있는", "따뜻한", "도전적인", "호기심많은", "진정한",
		"성실한", "날카로운", "총명한", "배려심있는", "침착한",
		"즐거운", "위풍당당한", "배고픈", "유연한", "의욕적인"
	};

	private final MemberRepository memberRepository;

	public String getRandomNickname() {
		String nickname;
		do {
			nickname = generateRandomNickname();
		} while (isNicknameExists(nickname));
		return nickname;
	}

	private String generateRandomNickname() {
		Random random = new Random();
		int animalIndex = random.nextInt(ANIMALS.length);
		int adjectiveIndex = random.nextInt(ADJECTIVES.length);
		return ADJECTIVES[adjectiveIndex] + ANIMALS[animalIndex] + generateRandomNumber();
	}

	private String generateRandomNumber() {
		Random random = new Random();
		return Integer.toString(random.nextInt(1000));
	}

	public boolean isNicknameExists(String nickname) {
		Optional<Member> existingMember = memberRepository.findByNickname(nickname);
		return existingMember.isPresent();
	}

	public boolean isNicknameExistsExcludeMe(Member member, String nickname) {
		Optional<Member> existingMember = memberRepository.findByNicknameAndIdNot(nickname, member.getId());
		return existingMember.isPresent();
	}
}
