package jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jpabook.jpashop.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) //다른 곳에서 new OrderItem(); order.setPrice() 같이 set으로 생성 막아줌
public class OrderItem {
    @Id
    @GeneratedValue //유사 seq
    @Column(name = "order_item_id") //PK
    private Long id;

    @ManyToOne(fetch = LAZY) //OrderItem(N) - item(1)
    @JoinColumn(name = "item_id")
    private Item item; //주문 상품

    @JsonIgnore
    @ManyToOne(fetch = LAZY) //OrderItem(N) - order(1)
    @JoinColumn(name = "order_id")
    private Order order; //주문

    private int orderPrice; //주문 가격

    private int count; //주문 수량

  /*  protected OrderItem() {
    }*/

    //==생성 메서드==//
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count);
        return orderItem;
    }

    //==비지니스 로직==//
    public void cancel() {
        getItem().addStock(count);
    }
    //==조회 로직==//

    /**
     * 주문상품 전체 가격 조회
     */
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}
