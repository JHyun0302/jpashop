package jpabook.jpashop.api;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.repository.order.simplequery.OrderSimpleQueryDto;
import jpabook.jpashop.repository.order.simplequery.OrderSimpleQueryRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * xToOne(ManyToOne, OneToOne) 관계 최적화
 * Order
 * Order -> Member
 * Order -> Delivery
 */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {
    private final OrderRepository orderRepository;
    private final OrderSimpleQueryRepository orderSimpleQueryRepository;

    /**
     * V1. 엔티티 직접 노출
     * - Hibernate5Module 모듈 등록, LAZY=null 처리
     * - 양방향 관계 문제 발생 -> @JsonIgnore
     * - 필요 없는 정보도 강제 지연 로딩 때문에 결과로 뜸!
     */
    @GetMapping("/api/v1/simple-orders")

    public List<Order> ordersV1() {
        List<Order> all = orderRepository.findAllByString(new OrderSearch());
        for (Order order : all) {
            order.getMember().getName(); //Lazy 강제 초기화
            order.getDelivery().getAddress(); //Lazy 강제 초기화
        }
        return all;
    }

    /**
     * V2. 엔티티를 조회해서 DTO로 변환(fetch join 사용X)
     * - 단점: 지연로딩으로 쿼리 N번 호출
     * <p>
     * order, member, delivery 엔티티 검색
     */
    @GetMapping("/api/v2/simple-orders")
    public Result ordersV2() {
        //N + 1 문제: 1번 쿼리 실행으로 N번 쿼리가 더 실행됨!
        //ORDER N = 2개 (order_id 2개)
        //1 + 회원 N + 배송 N = 총 쿼리 5번 실행
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());
        //엔티티 -> DTO 변환
        List<SimpleOrderDto> result = orders.stream()
                .map(o -> new SimpleOrderDto(o))
                .collect(toList());

        return new Result(result);
    }

    /**
     * V3. 엔티티를 조회해서 DTO로 변환(fetch join 사용O)
     * - fetch join으로 쿼리 1번 호출
     * 참고: fetch join에 대한 자세한 내용은 JPA 기본편 참고(정말 중요함)
     * trade off -> 모든 정보를 DB에서 퍼올려서 네트워크를 많이 잡아 먹지만 재사용 가능함!
     */
    @GetMapping("/api/v3/simple-orders")
    public Result ordersV3() {
        List<Order> orders = orderRepository.findAllWithMemberDelivery();
        //엔티티 -> DTO 변환
        List<SimpleOrderDto> result = orders.stream()
                .map(o -> new SimpleOrderDto(o))
                .collect(toList());

        return new Result(result);
    }

    /**
     * V4. JPA에서 DTO로 바로 조회
     * - 쿼리1번 호출
     * - select 절에서 원하는 데이터만 선택해서 조회
     * trade off -> 내가 필요한 정보만 DB에서 퍼올려서 네트워크는 적지만 코드 재사용을 못함!
     */
    @GetMapping("/api/v4/simple-orders")
    public List<OrderSimpleQueryDto> ordersV4() {
        return orderSimpleQueryRepository.findOrderDtos();
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }

    @Data
    static class SimpleOrderDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public SimpleOrderDto(Order order) {
            orderId = order.getId();
            name = order.getMember().getName(); //LAZY 초기화
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress(); //LAZY 초기화
        }
    }


}
