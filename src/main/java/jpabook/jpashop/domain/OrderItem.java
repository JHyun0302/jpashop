package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
public class OrderItem {
    @Id
    @GeneratedValue //유사 seq
    @Column(name = "order_item_id") //PK
    private Long id;

    @ManyToOne(fetch = LAZY) //OrderItem(N) - item(1)
    @JoinColumn(name = "item_id")
    private Item item; //주문 상품
    @ManyToOne(fetch = LAZY) //OrderItem(N) - order(1)
    @JoinColumn(name = "order_id")
    private Order order; //주문
    private int orderPrice; //주문 가격
    private int count; //주문 수량

}
