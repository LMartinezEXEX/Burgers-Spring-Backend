package com.burgers.app.Service;

import java.util.List;

import com.burgers.app.Domain.Role;
import com.burgers.app.Domain.User;
import com.burgers.app.Exception.BurgersException;

public interface UserService {
    
    public User getLoggedInUser() throws BurgersException;

    public List<User> getAll();

    public User add(Role role, User user);

    public User remove(Role role, User user);
}
