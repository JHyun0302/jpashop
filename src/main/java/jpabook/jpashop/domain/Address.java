package jpabook.jpashop.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

/**
 * @Setter 주지 않기: 값 변경 X
 * JPA 스팩상 자바 기본 생성자는 public 또는 protected 사용하기! (객체 생성시 리플랙션 같은 기술 사용해야함!)
 * new, 상속 금지
 */
@Embeddable //어딘가 내장 가능하다!
@Getter
public class Address {
    private String city;
    private String street;
    private String zipcode;

    protected Address() {
    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
