package com.burgers.app.Data;

import java.util.List;

import com.burgers.app.Domain.Order;
import com.burgers.app.Domain.User;

import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long>{

    List<Order> findAllByUser(User user);
}
