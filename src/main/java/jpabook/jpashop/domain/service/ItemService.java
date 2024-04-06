package jpabook.jpashop.domain.service;

import java.util.List;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true) // 기본적으로 조회 transaction
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    // 현재 구조를 보면 아이템 리포지토리에 이미 만든 함수를 바탕으로 서비스가 위임만 하고 있음.
    // 이렇게 단순한 경우에는 굳이 서비스를 따로 만들지 않고 컨트롤러에서 리포지토리 접근도 괜찮음.
    // MVC 구조를 암묵적으로 따르려고 하지 않고, 유연하게 작업하는 것이 좋다.

    @Transactional // 조회가 아닌 경우 오버라이딩 해서 해당 어노테이션 저장.
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    public List<Item> findItem() {
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }
}
