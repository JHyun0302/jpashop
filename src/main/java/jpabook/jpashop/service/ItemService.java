package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * ItemService는 ItemRepository에 단순히 위임만 하는 클래스 (굳이 필요있을까?)
 * Controller -> ItemRepository 로 바로 이동하는 것도 고려해보기!
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    /**
     * "변경 감지" 이용해서 update: 트랜잭션 commit 될 때 JPA는 flush 기능을 통해 변경된 사항을 확인함!
     * 아래 코드 == "merge" 동작 방식: DB에서 조회한 데이터를 findItem에 다 때려박고 반환시킴!
     */
    @Transactional
    public void updateItem(Long itemId, String name, int price, int stockQuantity) { //"UpdateItemDto updateItemDto" 파라미터로 주기
        Item findItem = itemRepository.findOne(itemId); //영속 상태(트랜잭션 안): 변경감지 대상!

        findItem.change(name, price, stockQuantity); //데이터 변경 지점 1곳으로 통일!
//        findItem.setName(name);
//        findItem.setPrice(price);
//        findItem.setStockQuantity(stockQuantity);
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }
}
