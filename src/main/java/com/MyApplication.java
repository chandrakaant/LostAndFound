package com;

import com.service.ItemService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

@SpringBootApplication
public class MyApplication implements CommandLineRunner {

    @Resource
    ItemService itemService;


    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class,args);
    }

    @Override
    public void run(String... args) throws Exception {
        itemService.deleteAll();
        itemService.init();
    }
}
