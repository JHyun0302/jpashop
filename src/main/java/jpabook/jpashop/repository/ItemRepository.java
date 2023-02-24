package jpabook.jpashop.repository;

import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {
    private final EntityManager em;

    public void save(Item item) {
        if (item.getId() == null) {
            em.persist(item);
        } else {
            Item merge = em.merge(item);//유사 update
            /**
             * item: 영속성 컨텍스트 X
             * merge: 영속성 컨텍스트에서 관리하는 객체(병합이 되고 관리대상임! -> 이후 사용시 merge 쓰기!)
             *
             * merge 보다는 변경 감지 이용!!!!
             * merge는 모든 속성값 변경 -> 값이 없으면 null!!
             */
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class).getResultList();
    }
}
