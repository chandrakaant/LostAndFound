package com;

import com.service.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

@SpringBootApplication
public class MyApplication implements CommandLineRunner {

    @Resource
    StorageService storageService;


    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class,args);
    }

    @Override
    public void run(String... args) throws Exception {
        storageService.deleteAll();
        storageService.init();
    }
}
