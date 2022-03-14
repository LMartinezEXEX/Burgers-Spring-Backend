package com.burgers.app.Service.Impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import com.burgers.app.Data.RoleRepository;
import com.burgers.app.Data.UserRepository;
import com.burgers.app.Domain.ERole;
import com.burgers.app.Domain.Role;
import com.burgers.app.Domain.User;
import com.burgers.app.Domain.UserDetailsImp;
import com.burgers.app.Exception.BurgersException;
import com.burgers.app.Request.ModifyRoleRequest;
import com.burgers.app.Service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public User getLoggedInUser() throws BurgersException {

        UserDetailsImp userDetails = (UserDetailsImp) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername());
        if(Objects.isNull(user)) throw new BurgersException("Error: User not found");

        return user;
    }

    @Override
    public List<User> getAll() {

        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add); // BLANK OUT PASSWORD!!

        return users;
    }

    @Override
    public User add(Role role, User user) {
        
        Set<Role> roles = user.getRoles();
        roles.add(role);

        user.setRoles(roles);
        userRepository.save(user);

        return user;
    }

    @Override
    public User remove(Role role, User user) {
        
        Set<Role> roles = user.getRoles();
        roles.remove(role);

        user.setRoles(roles);
        userRepository.save(user);

        return user;
    }

    @Override
    public Map<String, Object> requestToEntity(ModifyRoleRequest mRoleRequest) throws BurgersException {
        
        Map<String, Object> map = new HashMap<>();
        User user;
        Role role;

        switch(mRoleRequest.getRole()) {
            
            case "admin":
                role = roleRepository.findByName(ERole.ROLE_ADMIN);
                if(Objects.isNull(role)) throw new RuntimeException("Admin role not found!");
                break;

            case "mod":
                role = roleRepository.findByName(ERole.ROLE_MODERATOR);
                if(Objects.isNull(role)) throw new RuntimeException("Moderator role not found!");
                break;
            
            default:
                throw new BurgersException("Error: Unknown role");
        }

        Optional<User> optUser = userRepository.findById(mRoleRequest.getUserId());
        if(optUser.isEmpty()) throw new BurgersException("Error: User not found");

        user = optUser.get();

        map.put("User", user);
        map.put("Role", role);

        return map;
    }
}
