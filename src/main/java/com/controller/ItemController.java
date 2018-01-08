package com.controller;

import com.model.item.Item;
import com.model.item.ItemPojo;
import com.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

//@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;
/*
    @RequestMapping("/find/{itemName}")
    public Item getItem(@PathVariable("itemName") String itemName){
        return itemService.getItem(itemName);
    }
*/
    @RequestMapping("/user/upload/find")
    public Item getAllItems(){
        Item item = null;
        List<Item> list = itemService.getAllItems();
        for (Item i:list) {
            System.out.println(i.getItemName());
            item = i;
        }
        return item;
    }


    List<Item> itemList = new ArrayList<Item>();

    @GetMapping("/user/upload")
    public String uploadPage(ItemPojo itemPojo,Model model){
        model.addAttribute("itemPojo",itemPojo);
        return "upload";
    }

    @PostMapping("/user/upload")
    public String handleUploadFile(@RequestParam MultipartFile file,@RequestParam String  description, @RequestParam String state ,Model model) {

        ItemPojo itemPojo = new ItemPojo();
        itemPojo.setDecription(description);
        itemPojo.setState(state);
        itemPojo.setName(file.getOriginalFilename());

            Item item = itemService.addNewItem(itemPojo);
            try {
                itemService.store(file);
                model.addAttribute("message", "you successfully uploaded file " + file.getOriginalFilename());
                itemList.add(item);
            } catch (Exception e) {
                e.printStackTrace();
                model.addAttribute("message", "failed to upload file" + file.getOriginalFilename());
            }
            return "welcome";

    }

    @GetMapping("user/upload/files")
    public String getListFiles(Model model){
        for (Item item:itemList) {
            System.out.println(item.getItemName());
            model.addAttribute("files","File : "+ item.getItemName()+" "+item.getDescription()+" "+item.getState());
        }

        model.addAttribute("total files","Total files"+itemList.size());

        return "files";
    }

    @GetMapping("/file/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String fileName){
        Resource file = itemService.loadFile(fileName);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\""+ file.getFilename()+"\"").body(file);
    }

    @RequestMapping("/find/{itemId}")
    public Item getItem(@PathVariable("itemId") Integer itemId){
        return itemService.getItem(itemId);
    }

    @RequestMapping(method = RequestMethod.POST, value="/uploadfile")
    public Item addItem(@ModelAttribute("item") @Valid ItemPojo itemPojo){
        System.out.println(itemPojo.getName());
        System.out.println(itemPojo.getDescription());
        itemService.addNewItem(itemPojo);
        return null;
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
