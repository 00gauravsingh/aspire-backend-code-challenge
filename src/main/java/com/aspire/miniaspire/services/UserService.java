package com.aspire.miniaspire.services;

import com.aspire.miniaspire.domain.User;
import com.aspire.miniaspire.storage.UserStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserStore userStore;

    public List<User> getAllUsers(){
        return userStore.getAll();
    }

    public User getUserByCreds(String username, String password){
        return userStore.getUserNameWithCreds(username, password);
    }
}
