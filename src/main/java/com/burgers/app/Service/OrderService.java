package com.burgers.app.Service;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.burgers.app.Domain.Burger;
import com.burgers.app.Domain.Order;
import com.burgers.app.Domain.User;
import com.burgers.app.Exception.BurgersException;
import com.burgers.app.Request.OrderRequest;

public interface OrderService {
    
    public List<Burger> getBurgers(HttpSession session) throws BurgersException;

    public void sumbit(Order order, HttpSession session) throws BurgersException;

    public List<Order> getAllOrdersFromUser(User user) throws BurgersException;

    public Order requestToEntity(OrderRequest oRequest);
}
