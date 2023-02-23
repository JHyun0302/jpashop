package jpabook.jpashop.repository;

import jpabook.jpashop.domain.OrderStatus;
import lombok.Getter;
import lombok.Setter;

/**
 * 파라미터 조건(where 절)에 있으면 검색이 되어야함!
 */
@Getter
@Setter
public class OrderSearch {
    private String memberName; //회원 이름
    private OrderStatus orderStatus; //주문 상태[ORDER, CANCEL]

}
