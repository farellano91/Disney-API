package com.alkemy.disneyapi.security;

import com.alkemy.disneyapi.user.User;
import com.alkemy.disneyapi.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {

        this.userRepository = userRepository;

    }

    @Override
    public UserDetails loadUserByUsername(String email){

        Optional<User> jwtUser = userRepository.findByEmail(email);
        return jwtUser.map(user -> new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>())).orElse(null);

    }

}
