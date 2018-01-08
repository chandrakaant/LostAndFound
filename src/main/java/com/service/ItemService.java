package com.service;

import com.model.item.Item;
import com.model.item.ItemPojo;
import com.repository.ItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class ItemService{

    @Autowired
    private ItemRepository itemRepository;

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    private final Path rootLocation = Paths.get("//home//chandrakantpatel//uploads");


    public Item addNewItem(ItemPojo itemPojo){
        Item item = new Item();

        item.setItemName(itemPojo.getName());
        item.setDescription(itemPojo.getDescription());
        item.setState(itemPojo.getState());
        return itemRepository.save(item);
    }

    public void store(MultipartFile file){
        try{
            Files.copy(file.getInputStream(),this.rootLocation.resolve(file.getOriginalFilename()));
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public Resource loadFile(String fileName){
        Resource resource = null;
        try{
            Path file = rootLocation.resolve(fileName);
            resource = new UrlResource(file.toUri());
            if (resource.exists()||resource.isReadable()){
                return resource;
            }else{
                throw new RuntimeException("fail to load resource");
            }
        }catch (MalformedURLException exception){
            exception.printStackTrace();
        }
        return resource;
    }

    public void deleteAll(){
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    public void init(){
        try{
            Files.createDirectory(rootLocation);
        }catch(IOException e){
            throw new RuntimeException("could mot initialize storage");
        }
    }

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

