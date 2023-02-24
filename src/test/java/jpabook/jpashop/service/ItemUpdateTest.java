package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemUpdateTest {
    @Autowired
    EntityManager em;

    @Test
    public void updateTest() throws Exception {
        Book book = em.find(Book.class, 1L);

        //TX
        book.setName("asdfasdf");

        /**
         *  변경감지 == dirty checking
         *  setName() 값 바꾸고 트랜잭션 commit하면 JPA가 자동으로 update query 짜서 db에 반영함!
         */

        //TX commit


    }
}
