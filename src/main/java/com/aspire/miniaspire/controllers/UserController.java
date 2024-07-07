package com.aspire.miniaspire.controllers;

import com.aspire.miniaspire.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllUsers(@RequestHeader("Username") String username,
                                                  @RequestHeader("Password") String password) {
        if(username.equals("superadmin") && password.equals("superadmin")) {
            return ResponseEntity.ok(userService.getAllUsers());
        }
        return ResponseEntity.ok("Invalid credentials!!");
    }

}
