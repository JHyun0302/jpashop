package jpabook.jpashop.repository.order.simplequery;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * GetMapping("/api/v4/simple-orders")
 * 직접 select 절에서 원하는 데이터를 직접 선택 -> 직접 조회용으로 만들어서 재사용성 떨어짐!(폴더 만들어서 따로 저장)
 * Api 스펙 바뀌면 코드 뜯어 고쳐야함!
 */
@Repository
@RequiredArgsConstructor
public class OrderSimpleQueryRepository {
    private final EntityManager em;

    public List<OrderSimpleQueryDto> findOrderDtos() {
        return em.createQuery("select new jpabook.jpashop.repository.order.simplequery.OrderSimpleQueryDto(o.id, m.name, o.orderDate, o.status, d.address)" +
                        " from Order o" +
                        " join o.member m" +
                        " join o.delivery d", OrderSimpleQueryDto.class)
                .getResultList();
    }

}
