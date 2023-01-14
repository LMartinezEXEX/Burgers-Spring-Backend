package com.burgers.app.Web.REST;

import java.util.Map;

import javax.validation.Valid;

import com.burgers.app.DTO.Request.ModifyRoleDTO;
import com.burgers.app.Domain.Role;
import com.burgers.app.Domain.User;
import com.burgers.app.Exception.BurgersException;
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
}