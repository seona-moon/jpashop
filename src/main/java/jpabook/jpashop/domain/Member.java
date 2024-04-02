package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded // 내장 타입임을 알리는 코드.
    private Address address;

    @OneToMany(mappedBy = "member") // 멤버 입장에서 오더는 일대다관계. (연관관계의 거울. 주인member 필드를 매핑함!)
    private List<Order> orders = new ArrayList<>();
}
