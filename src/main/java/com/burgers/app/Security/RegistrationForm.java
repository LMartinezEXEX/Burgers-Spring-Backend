package com.burgers.app.Security;

import com.burgers.app.Domain.UserDetailsImp;

import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.Data;

@Data
public class RegistrationForm {
    private String username;
    private String password;
    private String fullName;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String phoneNumber;

    public UserDetailsImp toUser(PasswordEncoder passwordEncoder){
        return new UserDetailsImp(username, 
                        passwordEncoder.encode(password), 
                        fullName, 
                        street, 
                        city, 
                        state, 
                        zip, 
                        phoneNumber
                        );
    }
}
