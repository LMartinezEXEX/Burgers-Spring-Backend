package com.burgers.app.Web.REST;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.burgers.app.DTO.Request.OrderDTO;
import com.burgers.app.Domain.Order;
import com.burgers.app.Domain.User;
import com.burgers.app.Exception.BurgersException;
import com.burgers.app.Security.MessageResponse;
import com.burgers.app.Service.MapperService;
import com.burgers.app.Service.OrderService;
import com.burgers.app.Service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
@CrossOrigin(origins = "http://localhost:8081", allowedHeaders = "*", allowCredentials = "true" ,maxAge = 3600)
public class OrderRestController {
    
    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private MapperService mapperService;

    @PostMapping("/complete")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> completeOrder(@Valid @RequestBody OrderDTO oRequest, HttpServletRequest request) {

        Order orderData = mapperService.toEntity(oRequest);
        try {
            orderService.sumbit(orderData, request.getSession());
            return ResponseEntity.ok(new MessageResponse("Order submited succesful"));
        } catch (BurgersException be) {
            return ResponseEntity.badRequest().body(be.getMessage());
        }
    }

    @GetMapping("/get")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getOrders(){

        try {
            User user = userService.getLoggedInUser();
            List<Order> orders = orderService.getAllOrdersFromUser(user);
            orders.forEach(order -> mapperService.toDTO(order));
            return ResponseEntity.ok(orders);
        } catch (BurgersException be) {
            return ResponseEntity.badRequest().body(be.getMessage());
        } 
    }

    @GetMapping("/burgers")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getBurgersInCurrentOrder(HttpServletRequest request) {
        
        try {
            return ResponseEntity.ok(orderService.getBurgers(request.getSession()));
        } catch (BurgersException be) {
            return ResponseEntity.badRequest().body(be.getMessage());
        }
    }
}
