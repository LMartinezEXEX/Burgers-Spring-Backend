package com.burgers.app.Service.Impl;

import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpSession;

import com.burgers.app.Data.BurgerRepository;
import com.burgers.app.Data.OrderRepository;
import com.burgers.app.Data.UserRepository;
import com.burgers.app.Domain.Burger;
import com.burgers.app.Domain.Coupon;
import com.burgers.app.Domain.Order;
import com.burgers.app.Domain.User;
import com.burgers.app.Domain.UserDetailsImp;
import com.burgers.app.Exception.BurgersException;
import com.burgers.app.Service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BurgerRepository burgerRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<Burger> getBurgers(HttpSession session) throws BurgersException {

        Order order = (Order) session.getAttribute("userOrder");
        if(Objects.isNull(order)) throw new BurgersException("Error: Not order found");

        return order.getBurgers();
    }

    @Override
    public void sumbit(Order orderData, HttpSession session) throws BurgersException  {

        Order order = (Order) session.getAttribute("userOrder");
        if(Objects.isNull(order)) throw new BurgersException("Error: Not order found");
        
        if(!order.isValid()) throw new BurgersException("Error: Invalid order");

        UserDetailsImp userDetails = (UserDetailsImp) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername());
        if(Objects.isNull(user)) throw new BurgersException("Error: User not found");

        order.completeDeliveryData( user, 
                                    orderData.getName(), 
                                    orderData.getStreet(), 
                                    orderData.getCity(), 
                                    orderData.getState(), 
                                    orderData.getZip(), 
                                    orderData.isUsingFreeDelivery()
                                    );

        order.calculatePrice();

        Coupon coupon = (Coupon) session.getAttribute("userCoupon");
        if(Objects.nonNull(coupon)) {
            order.apply(coupon);
            session.removeAttribute("userCoupon");
        }

        for(Burger burger : order.getBurgers())
            burgerRepository.save(burger);
        
        orderRepository.save(order);

        user.addDeliveryPoint(1);
        userRepository.save(user);

        session.removeAttribute("userOrder");
    }

    @Override
    public List<Order> getAllOrdersFromUser(User user) throws BurgersException {
        
        List<Order> orders = orderRepository.findAllByUser(user);
        for(Order order : orders) // CHANGE TO SPECIFIC DTO!
            order.setUser(null);

        return orders;
    }
}
