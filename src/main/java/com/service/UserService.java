package com.service;

import com.Excpetion.EmailExistsException;
import com.model.user.User;
import com.model.user.UserPojo;
import com.model.VerificationToken;
import com.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import util.OtpGenerator;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UserService implements IUserService{

    private static final long EXPIRATION_TIME = 120000;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @Autowired
    private User user;

    @Autowired
    private Calendar calendar;



    public List<User> getAllUsers(){
       return (List<User>) userRepository.findAll();
    }


    public User registerNewUser(UserPojo userPojo) throws EmailExistsException {

        user = new User();
        user.setUserName(userPojo.getName());
        user.setEmail(userPojo.getEmail());
        user.setPhone(userPojo.getPhone());
        user.setPassword(passwordEncoder.encode(userPojo.getPassword()));
        user.setAddress(userPojo.getAddress());
        user.setAge(userPojo.getAge());
        user.setOtp(OtpGenerator.otp());
        user.setDateCreated(calendar.getTimeInMillis());

        if(emailExists(userPojo.getEmail())){
            throw new EmailExistsException("There is already an account with this email"+userPojo.getEmail());
        }else{
            sendConfirmationMail(user.getEmail(),user.getOtp(),"Registration Confirmation");
        }

        return userRepository.save(user);
    }

    public User findByOtp(String otp){
        return userRepository.findByOtp(otp);
    }

    public boolean enableUser(String otp){
        boolean flag = false;

        if (user.getOtp().equals(otp)){
            user.setEnabled(true);
            flag = true;
        }
        return flag;
    }

    public boolean isExpired(){

        if((user.getDateCreated()+EXPIRATION_TIME)-calendar.getTimeInMillis()<=0){
            System.out.println("inside method");
            return true;
        }

        return false;
    }

    private boolean emailExists(String email){
        User user = userRepository.findByEmail(email);
        if (user!=null){
            return true;
        }
        return  false;
    }


    public void sendConfirmationMail(String email,String otp,String message){

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject(message);
        mailMessage.setText("One Time password for your process is"+"\""+otp+"\"");
        emailService.sendMail(mailMessage);

    }


    public User getUser(String userName){
        return userRepository.findByUserName(userName);
    }

    @Override
    public void saveRegisteredUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void createVerificationToken(User user, String token) {

    }

    @Override
    public VerificationToken getVerificationToken() {
        return null;
    }

    public User getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public User getUserByPhone(long phone){
        return userRepository.findByPhone(phone);
    }

    public User getUser(Integer id){
        return userRepository.findOne(id);
    }

    public void addUser(User user){
        userRepository.save(user);
    }

    public void updateUser(User user){
        userRepository.save(user);
    }

    public void deleteUser(Integer id){
        userRepository.delete(id);
    }

    public void deleteUser(User user){
        userRepository.delete(user);
    }

}
