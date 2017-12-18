package com.service;

import com.Excpetion.EmailExistsException;
import com.model.Item;
import com.model.User;
import com.model.UserPojo;
import com.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService{

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers(){
       return (List<User>) userRepository.findAll();
    }

    public User registerNewUser(UserPojo userPojo) throws EmailExistsException {

        if(emailExists(userPojo.getEmail())){
            throw new EmailExistsException("There is already an account with this email"+userPojo.getEmail());
        }


        User user = new User();
        user.setUserName(userPojo.getName());
        user.setEmail(userPojo.getEmail());
        user.setPhone(userPojo.getPhone());
        user.setPassword(userPojo.getPassword());
        user.setAddress(userPojo.getAddress());
        user.setAge(userPojo.getAge());
        return userRepository.save(user);
    }

    private boolean emailExists(String email){
        User user = userRepository.findByEmail(email);
        if (user!=null){
            return true;
        }
        return  false;
    }

    public User getUser(String userName){
        return userRepository.findByUserName(userName);
    }

    public User loginUser(String email){
        return userRepository.findByEmail(email);
    }

    public User loginUser(long phone){
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
