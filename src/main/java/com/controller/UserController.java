package com.controller;

import com.Excpetion.EmailExistsException;
import com.model.user.LoginUser;
import com.model.user.User;
import com.model.user.UserPojo;
import com.model.VerificationToken;
import com.service.EmailService;
import com.service.UserService;
import jdk.nashorn.internal.runtime.options.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import util.OtpGenerator;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Calendar;
import java.util.Locale;
import java.util.Optional;

@Controller
public class UserController {


    @Autowired
    private UserService userService;

    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private EmailService emailService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @GetMapping("/")
    public String index(LoginUser loginUser){
        return "home";
    }

    @GetMapping("/login")
    public String home(){
        return "login";
    }

    @GetMapping("/register")
    public String showForm(UserPojo userPojo){
        return "form";
    }
//----------------------------------------------------------------------------------------------------------------
    //receive otp in this method to check through request param
    @RequestMapping(method = RequestMethod.GET,value = "/confirm_registration")
    private String confirmRegistration(WebRequest request,Model model,@RequestParam("token") String otp){

        if (otp==null){
            model.addAttribute("message","message");
            return "baduser";
        }
        if (userService.enableUser(otp)){
            model.addAttribute("registration confirmation","Registration Succesfull");
            return "/login";
        }else {
            return "baduser";
        }

    }

    @RequestMapping(method = RequestMethod.POST, value = "/register")
    public ModelAndView addUser(@ModelAttribute("user") @Valid UserPojo userPojo, BindingResult result, WebRequest request, Errors errors,Model model) {

        User user = new User();
        if (!result.hasErrors()) {
                user = createUserAccount(userPojo, result,model);
        }
        if (user == null) {
            result.rejectValue("email", "reg:error");
        }
        try {
            String appUrl = request.getContextPath();

        }catch (Exception e){
            return new ModelAndView("emailError","user",userPojo);
        }
        if (result.hasErrors()) {
            return new ModelAndView("register", "user", userPojo);
        } else {
            return new ModelAndView("confirm_registration", "user", userPojo);
        }
    }

    //private method to save user into database and check email
    private User createUserAccount(UserPojo userPojo,BindingResult result,Model model){
        User user = null;
        try{
             user = userService.registerNewUser(userPojo);
        }catch(EmailExistsException e){
            model.addAttribute("email","already exists");
            System.out.println("email already exists");
        }

        return user;

    }

//-----------------------------------------------------------------------------------------------------------
   @RequestMapping(method = RequestMethod.POST,value = "/user")
    public ModelAndView getUser(@Valid LoginUser loginUser, BindingResult result,WebRequest request,Model model){

        User user = new User();
        if (!result.hasErrors()){
            user = fetchUser(loginUser,result,model);
        }if (user == null){
            result.rejectValue("Either email","or password is incorrect");
       }
       if (result.hasErrors()){
            return new ModelAndView("login","user",loginUser);
       }else{
            return new ModelAndView("welcome","user",loginUser);
       }

    }

    private User fetchUser(LoginUser loginUser,BindingResult result,Model model){
        User user = null;
        user = userService.getUserByEmail(loginUser.getEmail());
        String suppliedPassword = bCryptPasswordEncoder.encode(loginUser.getPassword());
        if (bCryptPasswordEncoder.matches(user.getPassword(),suppliedPassword)){
            System.out.println(loginUser.getPassword());
            System.out.println(user.getPassword());
            model.addAttribute("username",user.getUserName());
            return user;
        }else{
           model.addAttribute("Password Mismatch");
        }
        return user;
    }


    @RequestMapping(method = RequestMethod.GET,value = "/logout")
    public String logout(){

        return "login";
    }

    @GetMapping("/forgot")
    private String getForgotPage(){
        return "forgot_password";
    }

    @PostMapping("/forgot")
    private String processForgotMail(@RequestParam("email") String email,Model model){
       User user = userService.getUserByEmail(email);
       System.out.println(user);
        if (user==null){
            System.out.println("something went wrong");
        }

        String otp = OtpGenerator.otp();
        user.setOtp(otp);
        userService.saveRegisteredUser(user);


        userService.sendConfirmationMail(email, otp,"Password Reset Token");


        return "setpassword";
    }

    private String setPassword(@RequestParam("token") String otp,@RequestParam("password") String password,@RequestParam("Re-Password")String repassword){
        if (userService.enableUser(otp)){
            if (password.equals(repassword)){
                User user = userService.findByOtp(otp);
                user.setPassword(password);
                userService.saveRegisteredUser(user);
            }
        }
        return "login";
    }

//-------------------------------------------------------------------------------------------------------------
    @RequestMapping(method = RequestMethod.PUT,value = "/user/update")
    public void updateUser(@RequestBody User user){
        userService.updateUser(user);
    }

    @RequestMapping(method = RequestMethod.DELETE,value = "/delete/{User}")
    public void deleteUser(@RequestBody User user){
        userService.deleteUser(user);
    }
}
