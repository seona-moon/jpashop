package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import java.util.List;
import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em; //영속성 컨테스트 관리


    public void save(Member member) {
        em.persist(member); //멤버를 영속 상태로 변경. 영속성 컨텍스트에 member를 올림
        //이때, Key값이 @Id(PK)가 됨. (commit시 insert 쿼리 수행)
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id); //(Type, pk)
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList(); //jpql은 sql과 유사하지만 프로그램 대상이 테이블이 아닌, Entity라는 점을 알아주면 된다.
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name =: name", Member.class).setParameter("name", name)
                .getResultList();
    }

}
