package com.service;

import com.Excpetion.EmailExistsException;
import com.model.user.User;
import com.model.user.UserPojo;
import com.model.VerificationToken;

public interface IUserService {
    User registerNewUser(UserPojo userPojo)throws EmailExistsException;



    User getUser(String verificationToken);

    void saveRegisteredUser(User user);


    void createVerificationToken(User user, String token);

    VerificationToken getVerificationToken();
}
