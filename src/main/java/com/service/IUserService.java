package com.service;

import com.Excpetion.EmailExistsException;
import com.model.User;
import com.model.UserPojo;

public interface IUserService {
    User registerNewUser(UserPojo userPojo)throws EmailExistsException;
}
