package com.burgers.app.Web.REST;

import com.burgers.app.Domain.User;
import com.burgers.app.Exception.BurgersException;
import com.burgers.app.Service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:8081", allowedHeaders = "*", allowCredentials = "true" ,maxAge = 3600)
public class UserRestController {
    
    @Autowired
    private UserService userService;

    @GetMapping("/deliveryPoints")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getDeliveryPoints() {

        try {
            User user = userService.getLoggedInUser();
            return ResponseEntity.ok(user.getDeliveryPoints());
        } catch (BurgersException be) {
            return ResponseEntity.badRequest().body(be.getMessage());
        }
    }

    /*
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/deliveryPoints")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getDeliveryPoints() {
        UserDetailsImp userDetails = (UserDetailsImp) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = userRepository.findByUsername(userDetails.getUsername());
        if (Objects.isNull(user)){
            return ResponseEntity.badRequest().body(new MessageResponse("Error: User not found!"));
        }

        return ResponseEntity.ok(user.getDeliveryPoints());
    }*/
}
