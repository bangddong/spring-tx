package stduy.springtx.propagation;

import static org.assertj.core.api.AssertionsForClassTypes.*;
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

	/**
	 * MemberService @Transactional: OFF
	 * MemberRepository @Transactional: ON
	 * LogRepository @Transactional: ON Exception
	 */
	@Test
	public void outerTxOff_fail() throws Exception {
		// given
		String username = "로그예외_outerTxOff_fail";

		// when
		assertThatThrownBy(() -> memberService.joinV1(username))
			.isInstanceOf(RuntimeException.class);

		// then: 완전히 롤백되지 않고, member 데이터가 남아서 저장된다.
		assertTrue(memberRepository.find(username).isPresent());
		assertTrue(logRepository.find(username).isEmpty());
	}

	/**
	 * MemberService @Transactional: ON
	 * MemberRepository @Transactional: OFF
	 * LogRepository @Transactional: OFF
	 */
	@Test
	public void singleTx() throws Exception {
		// given
		String username = "singleTx";

		// when
		memberService.joinV1(username);

		// then
		assertTrue(memberRepository.find(username).isPresent());
		assertTrue(logRepository.find(username).isPresent());
	}
}