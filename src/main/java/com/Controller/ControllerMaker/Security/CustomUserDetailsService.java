package com.Controller.ControllerMaker.Security;

import com.Controller.ControllerMaker.Exception.ResourceNotFoundException;
import com.Controller.ControllerMaker.Model.User;
import com.Controller.ControllerMaker.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

//        loading user from database by username
        User user = this.userRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email: "+ username, 0));

        return user;
    }
}
