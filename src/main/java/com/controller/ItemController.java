package com.controller;

import com.model.Item;
import com.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ItemController {

    @Autowired
    private ItemService itemService;
/*
    @RequestMapping("/find/{itemName}")
    public Item getItem(@PathVariable("itemName") String itemName){
        return itemService.getItem(itemName);
    }
*/
    @RequestMapping("/find")
    public Item getAllItems(){
        Item item = null;
        List<Item> list = itemService.getAllItems();
        for (Item i:list) {
            item = i;
        }
        return item;
    }

    @RequestMapping("/find/{itemId}")
    public Item getItem(@PathVariable("itemId") Integer itemId){
        return itemService.getItem(itemId);
    }

    @RequestMapping(method = RequestMethod.POST, value="/publish")
    public void addItem(@RequestBody Item item){
        itemService.addItem(item);
    }

    @RequestMapping (method = RequestMethod.PUT,value = "/find/itemId/{status}")
    public void updateItem(@RequestBody Item item,@PathVariable String state){
        itemService.updateItem(item,state);

    }

    @RequestMapping(method = RequestMethod.DELETE,value = "find/{itemId}")
    public void deleteUser(@PathVariable Integer itemId){
        itemService.deleteItem(itemId);
    }

}
