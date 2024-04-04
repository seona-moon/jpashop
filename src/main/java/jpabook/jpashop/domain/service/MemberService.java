package jpabook.jpashop.domain.service;

import java.util.List;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//jpa의 모든 데이터 변경이나 로직들은 가급적이면 트랜잭션 안에서 사용되어야 한다.
@Service
@Transactional(readOnly = true) // 읽기에는 가급적이면 read-only를 넣어주자.
@RequiredArgsConstructor //final 필드만 가지고 생성자를 만들어준다!
public class MemberService {

    private final MemberRepository memberRepository; // final : 바뀌지 않는 값이기도 하고, 컴파일 시 에러를 빠르게 판단 가능.

    /**
     * 회원 가입
     */
    @Transactional // 조회가 아닌 경우 read-only를 넣으면 변경되지 않음!!!
    public Long join(Member member) {
        validateDuplicateMember(member); //중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }


    private void validateDuplicateMember(Member member) {
        // EXCEPTION
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) { //이미 있는 이름이라면
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * 회원 전체 조회
     */

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /**
     * 회원 단건 조회
     */
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}
