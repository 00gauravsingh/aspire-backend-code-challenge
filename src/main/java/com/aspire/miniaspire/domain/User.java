package com.aspire.miniaspire.domain;

import com.aspire.miniaspire.enums.UserType;
import lombok.Data;

@Data
public class User {
    private Integer id;
    private String username;
    private String password;
    private UserType userType;
}
