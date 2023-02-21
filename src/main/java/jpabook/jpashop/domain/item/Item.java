package jpabook.jpashop.domain.item;

import jpabook.jpashop.domain.Category;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * abstract: 상속 관계 맵핑
 * 부모 클래스에 상속 관계 전략 지정(@Inheritance)
 * InheritanceType.SINGLE_TABLE: 한 테이블에 다 때려박기, .JOINED: 정규화된 스타일, .TABLE_PER_CLASS: 각각 테이블(album, book, movie)
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype") //"SINGLE_TABLE" 이므로 저장할 때 구분할 수 있어야함!
@Getter
@Setter
public abstract class Item {
    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;

    private int price;

    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();
}
