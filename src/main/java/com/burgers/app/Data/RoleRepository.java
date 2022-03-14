package com.burgers.app.Data;

import com.burgers.app.Domain.ERole;
import com.burgers.app.Domain.Role;

import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long>{
    
    Role findByName(ERole name);
}
