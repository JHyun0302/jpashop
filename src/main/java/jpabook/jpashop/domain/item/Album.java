package jpabook.jpashop.domain.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("A") //DB가 singleTable 다 때려박을 때 구분 할 수 있게끔
@Getter
@Setter
public class Album extends Item {
    private String artist;
    private String etc;
}
