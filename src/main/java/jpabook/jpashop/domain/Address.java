package jpabook.jpashop.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class Address {
    private String city;
    private String street;
    private String zipcode;

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }

    protected Address() { //Jpa 스펙을 위해 기본 생성자 만들기. protected를 달아 외부에서 함부로 만들지 못하도록.
    }
}
