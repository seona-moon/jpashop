package jpabook.jpashop.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {
    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    // 양방향 관계에서 연관관계의 주인을 잡자. 여기에서만 변경하기!
    @ManyToOne(fetch = FetchType.LAZY) // Order 입장에서 멤버는 다대일관계. (fk 보유)
    @JoinColumn(name = "member_id") //외래키
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL) //연관관계의 거울
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL) //일대일 관계 (FK를 어디에 둘지 결정해야함.)
    @JoinColumn(name = "delivery_id") //여기에 FK를 두기. 연관관계의 주인
    private Delivery delivery;

    private LocalDateTime orderDate; //주문 시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status; //주문 상태 [ORDER, CANCEL]

    //==연관 관계 메서드==//
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    //protected Order(){} // 생성 메서드가 난잡해지지 않기 위해, protected로 제약 놓기. -> NoArgConstructor 어노테이션 가능

    //==생성 메서드==//
    // 복잡한 생성자 관계를 하나의 메서드에서 지정해줄 수 있다.
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    //==비즈니스 로직==//

    /**
     * 주문 취소
     */
    public void cancle() {
        if (delivery.getStatus() == DeliveryStatus.COMP) { // 주문 완료된 상품
            throw new IllegalStateException("이미 배송 완료된 상품은 취소가 불가능합니다.");
        }
        this.setStatus(OrderStatus.CANCEL);
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel(); // 주문 상품마다 재고 수량을 원복해줘야 함.
        }
    }

    //==조회 로직==//

    /**
     * 전체 주문 가격 조회
     */
    public int getTotalPrice() {
        int totalPrice = 0;
        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice(); // 아이템마다 주문 가격과 수량을 곱한 총 금액 계산
        }
        return totalPrice;
        //return orderItems.stream().mapToInt(OrderItem::getTotalPrice).sum(); (스트림을 이용한 방법)
    }
}
