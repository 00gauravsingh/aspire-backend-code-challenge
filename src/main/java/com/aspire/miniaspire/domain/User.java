package com.aspire.miniaspire.domain;

import com.aspire.miniaspire.enums.UserType;
import lombok.Data;

@Data
public class User {
    Integer id;
    String username;
    String password;
    UserType userType;
}
