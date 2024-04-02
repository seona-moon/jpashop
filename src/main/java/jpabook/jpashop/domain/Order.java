package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {
    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    // 양방향 관계에서 연관관계의 주인을 잡자. 여기에서만 변경하기!
    @ManyToOne // Order 입장에서 멤버는 다대일관계. (fk 보유)
    @JoinColumn(name = "member_id") //외래키
    private Member member;

    @OneToMany(mappedBy = "order") //연관관계의 거울
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne //일대일 관계 (FK를 어디에 둘지 결정해야함.)
    @JoinColumn(name = "delivery_id") //여기에 FK를 두기. 연관관계의 주인
    private Delivery delivery;

    private LocalDateTime orderDate; //주문 시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status; //주문 상태 [ORDER, CANCEL]
}
