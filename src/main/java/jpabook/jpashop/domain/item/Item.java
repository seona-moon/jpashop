package jpabook.jpashop.domain.item;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;
import jpabook.jpashop.domain.Category;
import jpabook.jpashop.domain.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) //상속관계 전략을 부모 테이블에 지정
@DiscriminatorColumn(name = "dtype") //싱글 테이블 내의 아이템들이 어떤 타입인지 (B, A, M)
@Getter
@Setter
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    // 공통속성
    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    //==재고를 늘리고 줄이는 로직 비즈니스 로직==//
    // 엔티티 내부에서 관리할 수 있는 로직이라면, 그 데이터를 가지고 있는 엔티티가 맡는게 가장 이상적인 객체지향적 특성이다.
    // Setter로 외부에서 값을 변경하는 것보다, 가능한 한 엔티티 내부에 비즈니스 로직을 작성하자.

    /**
     * stock 증가
     */
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    /**
     * stock 감소
     */
    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0) { // 현재 수량보다 빼려는 수량이 많다면
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }

}
