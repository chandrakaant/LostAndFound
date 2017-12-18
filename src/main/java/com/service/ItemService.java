package com.service;

import com.model.Item;
import com.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService{

    @Autowired
    private ItemRepository itemRepository;
/*
    public Item getItem(String itemName){
        return itemRepository.findByName(itemName);
    }
*/
    public Item getItem(Integer id){
        return itemRepository.findOne(id);
    }

    public List<Item> getAllItems(){
        return (List<Item>) itemRepository.findAll();
    }

    public void addItem(Item item){
        itemRepository.save(item);
    }

    public void updateItem(Item item,String status){
        itemRepository.save(item);
    }

    public void deleteItem(Item item){
        itemRepository.delete(item);
    }

    public void deleteItem(Integer id){
        itemRepository.delete(id);
    }
}

