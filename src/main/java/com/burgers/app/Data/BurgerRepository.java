package com.burgers.app.Data;

import com.burgers.app.Domain.Burger;

import org.springframework.data.repository.CrudRepository;

public interface BurgerRepository extends CrudRepository<Burger, Long>{
    
}
