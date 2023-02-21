package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
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

    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member") //Order 필드의 member로 mapping 되어짐!(거울 - 읽기전용; 값을 넣어도 FK값 변경 안됨!)
    private List<Order> orders = new ArrayList<>();
}
