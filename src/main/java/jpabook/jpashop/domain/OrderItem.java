package jpabook.jpashop.domain;

import jakarta.persistence.*;
import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "order_item")
@Getter
@Setter
public class OrderItem {
    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY) //하나의 오더가 여러개의 오더 아이템을 가질 수 있음.
    @JoinColumn(name = "order_id") //fk (연관관계의 주인)
    private Order order;

    private int orderPrice; //주문 가격
    private int count; //주문 수량
}
