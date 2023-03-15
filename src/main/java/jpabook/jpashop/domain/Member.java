package jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member {
    @Id
    @GeneratedValue //유사 seq
    @Column(name = "member_id") //PK 컬럼명
    private Long id;

    @NotEmpty
    private String name;

    @Embedded //내장 타입
    private Address address;

    @JsonIgnore //순수하게 회원 정보만 api로 받을 때 order 정보 없애기
    @OneToMany(mappedBy = "member") //Order 필드의 member로 mapping 되어짐!(거울 - 읽기전용; 값을 넣어도 FK값 변경 안됨!)
    private List<Order> orders = new ArrayList<>();
}
