package jpabook.jpashop;

import com.fasterxml.jackson.databind.deser.std.StdKeyDeserializer;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTest {
    @Autowired MemberRepository memberRepository;

    @Test
    @Transactional //테스트에 있으면 테스트가 끝난 후 db를 롤백해버린다. (반복적인 테스트를 위해)
    public void testMember() throws Exception {
        //given
        Member member = new Member();
        member.setUsername("memberA");
        
        //when
        Long saveId = memberRepository.save(member);
        Member findMember = memberRepository.find(saveId);

        //then
        Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        // 엔티티 매니저를 통한 모든 데이터 변경은 반드시 트랜젝션 안에서만 이루어져야한다. (ERROR)
        // -> @Transactional 추가

        Assertions.assertThat(findMember).isEqualTo(member);
        // 같은 영속성 컨텍스트 안에서, 식별자가 같으면 같은 엔티티로 관리된다.
    }
}