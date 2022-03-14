package com.burgers.app.Security;

import com.burgers.app.Data.UserRepository;
import com.burgers.app.Domain.User;
import com.burgers.app.Domain.UserDetailsImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
    
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user != null)
            return UserDetailsImp.build(user);
        
        throw new UsernameNotFoundException("User '" + username + "' not found");
    }
}
