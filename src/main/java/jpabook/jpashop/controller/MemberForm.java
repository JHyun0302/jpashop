package jpabook.jpashop.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

/**
 * Member(Entity)를 직접 사용 X: 엔티티는 핵심 비지니스 로직만 가지고 있고 화면을 위한 로직은 없어야함!
 */
@Getter
@Setter
public class MemberForm {
    @NotEmpty(message = "회원 이름은 필수 입니다.")
    private String name;
    private String city;
    private String street;
    private String zipcode;
}
