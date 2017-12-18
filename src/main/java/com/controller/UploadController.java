package com.controller;

import com.model.Item;
import com.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;


@Controller
public class UploadController {

    @Autowired
    private StorageService storageService;

    List<Item> itemList = new ArrayList<Item>();


    @PostMapping("/upload")
    public String handleUploadFile(@RequestParam("file")MultipartFile file, @RequestParam("description")String description, @RequestParam("state") String state, Model model){
        Item item = new Item(file.getOriginalFilename(),description,state);

        try{
            storageService.store(file);
            model.addAttribute("message","you succesfully uploades file "+file.getOriginalFilename());
            itemList.add(item);
        }catch(Exception e){
            e.printStackTrace();
            model.addAttribute("message","failed to upload file"+file.getOriginalFilename());
        }
    return "upload";

    }

    @GetMapping("/getAllFiles")
    public String getListFiles(Model model){
        for (Item item:itemList) {
            model.addAttribute("files","File : "+ item.getItemName()+" "+item.getDescription()+" "+item.getState());
        }

        model.addAttribute("total files","Total files"+itemList.size());

        return "filelist";
    }

    @GetMapping("/file/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String fileName){
        Resource file = storageService.loadFile(fileName);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\""+ file.getFilename()+"\"").body(file);
    }

}
