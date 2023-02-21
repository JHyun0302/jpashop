package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "orders") //Table명 "orders"로 변경
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;
    /**
     * Member & Order 연관관계 주인 = Order(FK와 가까운 놈)
     * Member(거울, 읽기전용)
     *
     * @X ToOne(default fetch = .EAGER) -> 즉시로딩 (다 .LAZY로 바꿔주기!)
     */
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")//join 뭘로 할건지(FK)
    private Member member; //주문 회원

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL) //거울: Order(1) - orderItemList(N)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id") // FK: 1대 1관계에서는 누가 더 데이터 접근 많이 하는지? ex) 주문정보를 보고 배달정보를 확인!
    private Delivery delivery; //배송정보

    private LocalDateTime orderDate; //주문시간

    @Enumerated(EnumType.STRING) //enum 쓸 때 필수! <무조건 .String 쓰기!>
    private OrderStatus status; //주문상태 [ORDER, CANCEL]

    //==연관관계 메서드==// 양방향 관계에서 값 저장할 때 양쪽 모두 set! (위치: 핵심적으로 컨트롤 하는 곳!)

    /**
     * <member & order> 양방향 관계에서
     * 갱신된 order값을 <Order, Member>에도 set해주기!
     */
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

}