package stduy.springtx.propagation;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class MemberServiceTest {

	@Autowired
	MemberService memberService;
	@Autowired
	MemberRepository memberRepository;
	@Autowired
	LogRepository logRepository;

	/**
	 * MemberService @Transactional: OFF
	 * MemberRepository @Transactional: ON
	 * LogRepository @Transactional: ON
	 */
	@Test
	public void outerTxOff_success() throws Exception {
	    // given
		String username = "outerTxOff_success";

	    // when
		memberService.joinV1(username);

	    // then
		assertTrue(memberRepository.find(username).isPresent());
		assertTrue(logRepository.find(username).isPresent());
	}
}