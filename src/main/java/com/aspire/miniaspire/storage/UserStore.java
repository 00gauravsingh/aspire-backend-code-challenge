package com.aspire.miniaspire.storage;

import com.aspire.miniaspire.domain.User;
import com.aspire.miniaspire.enums.UserType;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserStore {

    List<User> userList = new ArrayList<>();

    @PostConstruct
    public void init() {
        User user1 = new User();
        user1.setId(1);
        user1.setUsername("aspire");
        user1.setPassword("aspire");
        user1.setUserType(UserType.ADMIN);
        this.addUser(user1);

        User user2 = new User();
        user2.setId(2);
        user2.setUsername("customer");
        user2.setPassword("customer");
        user2.setUserType(UserType.CUSTOMER);
        this.addUser(user2);

        User user3 = new User();
        user3.setId(3);
        user3.setUsername("customer2");
        user3.setPassword("customer2");
        user3.setUserType(UserType.CUSTOMER);
        this.addUser(user3);
    }

    public List<User> getAll(){
        return userList;
    }

    public boolean addUser(User newUser){
        for(User user: userList){
            if(user.getUsername().equals(newUser.getUsername()) || user.getId() == newUser.getId()){
                return false;
            }
        }
        userList.add(newUser);
        return true;
    }

    public User getUserNameWithCreds(String username, String password){
        for(User user : userList){
            if(user.getUsername().equals(username) && user.getPassword().equals(password)){
                return user;
            }
        }
        return null;
    }
}
