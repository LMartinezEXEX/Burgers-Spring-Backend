package com.burgers.app.Data;

import com.burgers.app.Domain.BurgerSize;

import org.springframework.data.repository.CrudRepository;

public interface BurgerSizeRepository extends CrudRepository<BurgerSize, Long> {
    
    BurgerSize findByName(String name);
}
