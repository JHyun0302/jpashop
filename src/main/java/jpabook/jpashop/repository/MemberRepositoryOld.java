package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryOld {
    /**
     * 전
     *
     * @PersistenceContext : 스프링이 엔티티 메니저(EntityManager) 주입
     * 스프링부트 라이브러리 사용하면 @Autowired로 치환가능! -> 생성자 주입으로 바꾸면 생략가능!
     */
//    @PersistenceContext
    private final EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList(); // (jpql, 반환 type)
    }

    /**
     * sql: Table 대상으로 쿼리
     * jpql: 객체(Member) 대상으로 쿼리
     */

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class) //":name" 파라미터 바인딩
                .setParameter("name", name)
                .getResultList();
    }

}
