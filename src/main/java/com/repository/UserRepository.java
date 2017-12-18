package com.repository;

import com.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Integer> {
    User findByUserName(String user_name);
    User findByEmail(String email);
    User findByPhone(long phone);

}
