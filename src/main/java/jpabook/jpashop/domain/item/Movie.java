package jpabook.jpashop.domain.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("M") //singleTable 전략이여서 다 때려박고 구분 할 수 있게끔
@Getter
@Setter
public class Movie extends Item {
    private String director;
    private String actor;
}
