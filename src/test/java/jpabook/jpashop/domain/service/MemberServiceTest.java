package jpabook.jpashop.domain.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

// 우리는 JPA가 실제 DB와 연동되어 도는걸 확인하는게 중요함. 따라서 스프링과 인티그레이션 하기 위해 위의 두 어노테이션 추가하기.
@RunWith(SpringRunner.class)
@SpringBootTest // 스프링 부트를 띄운 상태로 테스트하려면 설정해줘야함.
@Transactional // 데이터 건드릴때, 이게 있어아 롤백이 됨.
public class MemberServiceTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberService memberService;
    @Autowired
    EntityManager em;

    //스프링 트랜잭션은 커밋을 안하고 롤백하게 됨. 하지만 영속성 컨텍스트의 값들은 커밋되어야 Insert쿼리가 동작함.
    @Test
    public void 회원가입() throws Exception {
        //given (이게 주어졌을 떄)
        Member member = new Member();
        member.setName("Kim");

        //when (이러한 상황이 발생하면)
        Long savedId = memberService.join(member);

        //then (이런 결과가 나와야 함)
        //JPA에서 같은 트랜젝션 안에서 같은 엔티티가 있다면, 같은 영속성 컨텍스트에 하나만 저장됨.
        Assert.assertEquals(member, memberRepository.findOne(savedId)); //저장된 멤버와 현 멤버가 같은지 확인.
    }

    @Test(expected = IllegalStateException.class) //해당 케이스에서는 에러가 나와야지 성공으로 간주함!
    public void 중복_회원_예외() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("Kim");

        Member member2 = new Member();
        member2.setName("Kim");

        //when
        memberService.join(member1);
        memberService.join(member2); //예외 발생
    }
}