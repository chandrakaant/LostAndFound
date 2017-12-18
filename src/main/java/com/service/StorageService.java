package com.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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


@Service
public class StorageService {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    private final Path rootLocation = Paths.get("//home//chandrakanthpatel//uploads");

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

}
