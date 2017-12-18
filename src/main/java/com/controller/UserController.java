package com.controller;

import com.Excpetion.EmailExistsException;
import com.model.Item;
import com.model.User;
import com.model.UserPojo;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {


    @Autowired
    private UserService userService;
    private User user;

    @GetMapping("/")
    public String index(){
        return "login";
    }

    @GetMapping("/register")
    public String showForm(UserPojo userPojo){
        return "form";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/register")
    public ModelAndView addUser(@ModelAttribute("user") @Valid UserPojo userPojo, BindingResult result, WebRequest request, Errors errors) {

        User user = new User();
        if (!result.hasErrors()) {
            user = createUserAccount(userPojo, result);
        }
        if (user == null) {
            result.rejectValue("email", "reg:error");
        }
        if (result.hasErrors()) {
            return new ModelAndView("register", "user", userPojo);
        } else {
            return new ModelAndView("result", "user", userPojo);
        }
    }


    private User createUserAccount(UserPojo userPojo,BindingResult result){
        User user = null;
        try{
             user = userService.registerNewUser(userPojo);
        }catch(EmailExistsException e){
            e.printStackTrace();
            return null;
        }

        return user;

    }




    @RequestMapping(method = RequestMethod.POST,value = "/login")
    public User getUser(){


    }

    @RequestMapping(method = RequestMethod.PUT,value = "/update")
    public void updateUser(@RequestBody User user){
        userService.updateUser(user);
    }

    @RequestMapping(method = RequestMethod.DELETE,value = "/delete/{User}")
    public void deleteUser(@RequestBody User user){
        userService.deleteUser(user);
    }
}
