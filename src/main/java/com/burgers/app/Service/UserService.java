package com.burgers.app.Service;

import java.util.List;
import java.util.Map;

import com.burgers.app.Domain.Role;
import com.burgers.app.Domain.User;
import com.burgers.app.Exception.BurgersException;
import com.burgers.app.Request.ModifyRoleRequest;

public interface UserService {
    
    public User getLoggedInUser() throws BurgersException;

    public List<User> getAll();

    public User add(Role role, User user);

    public User remove(Role role, User user);

    public Map<String, Object> requestToEntity(ModifyRoleRequest mRoleRequest) throws BurgersException;
}
