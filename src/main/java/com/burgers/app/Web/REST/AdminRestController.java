package com.burgers.app.Web.REST;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import com.burgers.app.DTO.Request.ModifyRoleDTO;
import com.burgers.app.Data.RoleRepository;
import com.burgers.app.Data.UserRepository;
import com.burgers.app.Domain.ERole;
import com.burgers.app.Domain.Role;
import com.burgers.app.Domain.User;
import com.burgers.app.Exception.BurgersException;
import com.burgers.app.Security.MessageResponse;
import com.burgers.app.Service.MapperService;
import com.burgers.app.Service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:8081", allowedHeaders = "*", allowCredentials = "true" ,maxAge = 3600)
public class AdminRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private MapperService mapperService;

    @GetMapping("/getUsers")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllUsers() {

        return ResponseEntity.ok(userService.getAll());
    }

    @PostMapping("/addRole")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addRole(@Valid @RequestBody ModifyRoleDTO mRoleRequest) {

        try {
            Map<String, Object> map = mapperService.toEntity(mRoleRequest);
            User user = (User) map.get("User");
            Role role = (Role) map.get("Role");

            return ResponseEntity.ok(userService.add(role, user));
        } catch(BurgersException be) {
            return ResponseEntity.badRequest().body(be.getMessage());
        }
    }

    @PostMapping("/removeRole")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> removeRole(@Valid @RequestBody ModifyRoleDTO mRoleRequest) {

        try {
            Map<String, Object> map = mapperService.toEntity(mRoleRequest);
            User user = (User) map.get("User");
            Role role = (Role) map.get("Role");

            return ResponseEntity.ok(userService.remove(role, user));
        } catch(BurgersException be) {
            return ResponseEntity.badRequest().body(be.getMessage());
        }
    }

    /*
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @GetMapping("/getUsers")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllUsers() {
        Iterable<User> users = userRepository.findAll();

        for(User user : users){
            user.setPassword("");
        }

        return ResponseEntity.ok(users);
    }

    @PostMapping("/addRole")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addRole(@Valid @RequestBody ModifyRoleRequest request){

        Optional<User> opt_user;
        User user;
        Role role;

        switch(request.getRole()) {
            case "admin":
                role = roleRepository.findByName(ERole.ROLE_ADMIN);
                if (Objects.isNull(role))
                    throw new RuntimeException("Error: Role is not found");

                break;
            
            case "mod":
                role = roleRepository.findByName(ERole.ROLE_MODERATOR);
                if (Objects.isNull(role))
                    throw new RuntimeException("Error: Role is not found");

                break;
            
            default:
                return ResponseEntity.badRequest().body(new MessageResponse("Error: Invalid role to add"));
        }

        try {

            opt_user = userRepository.findById(request.getUserId());
            user = opt_user.get();

        } catch (NoSuchElementException nsee) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: User not found"));
        }

        Set<Role> userRoles = user.getRoles();
        userRoles.add(role);

        user.setRoles(userRoles);

        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("Role added succesfull"));
    }

    @PostMapping("/removeRole")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> removeRole(@Valid @RequestBody ModifyRoleRequest request){
        Optional<User> opt_user;
        User user;
        Role role;

        switch(request.getRole()) {
            case "admin":
                role = roleRepository.findByName(ERole.ROLE_ADMIN);
                if (Objects.isNull(role))
                    throw new RuntimeException("Error: Role is not found");

                break;
            
            case "mod":
                role = roleRepository.findByName(ERole.ROLE_MODERATOR);
                if (Objects.isNull(role))
                    throw new RuntimeException("Error: Role is not found");

                break;
            
            default:
                return ResponseEntity.badRequest().body(new MessageResponse("Error: Invalid role to add"));
        }

        try {

            opt_user = userRepository.findById(request.getUserId());
            user = opt_user.get();

        } catch (NoSuchElementException nsee) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: User not found"));
        }

        Set<Role> userRoles = user.getRoles();
        userRoles.remove(role);

        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("Role removed succesfull"));
    }*/
}