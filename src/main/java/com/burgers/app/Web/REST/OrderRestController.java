package com.burgers.app.Web.REST;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.burgers.app.Domain.Order;
import com.burgers.app.Domain.User;
import com.burgers.app.Exception.BurgersException;
import com.burgers.app.Request.OrderRequest;
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
    public ResponseEntity<?> completeOrder(@Valid @RequestBody OrderRequest oRequest, HttpServletRequest request) {

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
            return ResponseEntity.ok(orderService.getAllOrdersFromUser(user));
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

    /*
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BurgerRepository burgerRepository;

    @GetMapping("/burgers")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getBurgersInCurrentOrder(HttpServletRequest request){
        Order order = (Order) request.getSession().getAttribute("userOrder");
        if(order == null){
            log.info("No se encontro la order para obtener las burgers!");
            return ResponseEntity.badRequest().body(new MessageResponse("Error: No order initiated!"));
        }
            
        return ResponseEntity.ok(order.getBurgers());
    }

    @PostMapping("/complete")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> completeOrder(@Valid @RequestBody OrderRequest orderRequest, HttpServletRequest request) {
        
        Long deliveryCost = 120L;

        Order order = (Order) request.getSession().getAttribute("userOrder");
        if(order == null){
            return ResponseEntity.badRequest().body(new MessageResponse("Error: No order created!"));
        }

        if(order.getBurgers().size() <= 0){
            return ResponseEntity.badRequest().body(new MessageResponse("Error: No burger in current order!"));
        }

        UserDetailsImp userDetails = (UserDetailsImp) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = userRepository.findByUsername(userDetails.getUsername());
        if (user == null){
            return ResponseEntity.badRequest().body(new MessageResponse("Error: User not found!"));
        }
        
        if(orderRequest.isFreeDelivery()){
            if(user.getDeliveryPoints() != 10){
                return ResponseEntity.badRequest().body(new MessageResponse("Error: Not enough point to apply!"));
            }
            else deliveryCost = 0L;
        }
        
        order.setUser(user);
        order.setName(orderRequest.getName());
        order.setStreet(orderRequest.getStreet());
        order.setCity(orderRequest.getCity());
        order.setState(orderRequest.getState());
        order.setZip(orderRequest.getZip());
        order.setPrice(deliveryCost);

        for (Burger burger : order.getBurgers()){
            if(!burger.isValid())
            return ResponseEntity.badRequest().body(new MessageResponse(String.format("Error: Burger with id: %d is not valid!", burger.getBurgerInOrderId())));
        }

        for(Burger burger : order.getBurgers()){
            burgerRepository.save(burger);
            order.setPrice(order.getPrice() + burger.getPrice());
        }

        Coupon coupon = (Coupon) request.getSession().getAttribute("userCoupon");
        if(!Objects.isNull(coupon)){
            log.info("Coupon applied!");
            Long discountedPrice = order.getPrice() - coupon.getDiscount();
            
            Long finalPrice = (discountedPrice >= 0) ? discountedPrice : 0;
            order.setPrice(finalPrice);

            request.getSession().removeAttribute("userCoupon");
        }

        orderRepository.save(order);

        user.addDeliveryPoint(1);
        userRepository.save(user);

        request.getSession().removeAttribute("userOrder");

        return ResponseEntity.ok(new MessageResponse("Order succesfull!"));
    }

    @GetMapping("/get")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getOrders(){

        UserDetailsImp userDetails = (UserDetailsImp) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername());
        if (user == null){
            return ResponseEntity.badRequest().body(new MessageResponse("Error: User not found!"));
        }

        List<Order> orders = orderRepository.findAllByUser(user);

        for(Order order : orders){
            order.setUser(null);
        }

        return ResponseEntity.ok(orders);
    } */
}
