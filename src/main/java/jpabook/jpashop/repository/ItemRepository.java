package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import java.util.List;
import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ItemRepository {
    private final EntityManager em;

    public void save(Item item) {
        // 새로 생성한 아이템은 처음에 아이디가 없음.(JPA가 지정하기 전에) > 신규로 등록하기 위해 persist로 저장
        if (item.getId() == null) {
            em.persist(item);
        } else { // 아이디가 있다면 이미 DB에 저장된 값. merge를 통해 업데이트 한다고 생각해주자.
            em.merge(item);
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() { // 단건 조회가 아닌 경우 sql 필요
        return em.createQuery("select i from Item i", Item.class).getResultList();
    }
}
