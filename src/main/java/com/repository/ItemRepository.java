package com.repository;

import com.model.item.Item;
import org.springframework.data.repository.CrudRepository;

public interface ItemRepository  extends CrudRepository<Item,Integer> {
    //public Item findByName(String itemName);
}
