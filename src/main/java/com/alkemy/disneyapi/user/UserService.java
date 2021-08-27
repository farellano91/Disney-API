package com.alkemy.disneyapi.user;

import com.alkemy.disneyapi.mapstruct.dtos.UserDto;
import com.alkemy.disneyapi.security.CustomUserDetailsService;
import com.alkemy.disneyapi.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService{

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;

    @Autowired
    public UserService(UserRepository userRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder,
                       AuthenticationManager authenticationManager,
                       JwtUtils jwtUtil,
                       CustomUserDetailsService customUserDetailsService) {

        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.customUserDetailsService = customUserDetailsService;
    }

    public boolean checkEmailExistence(String email) {

        return userRepository.existsByEmail(email);

    }

    public void saveUser(UserDto userToSave) {

        User user = new User();
        user.setEmail(userToSave.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(userToSave.getPassword()));
        userRepository.save(user);

    }

    public String logInUser(UserDto user) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getEmail(),
                        user.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(user.getEmail());

        if (userDetails == null) {

            return null;

        } else {

            return jwtUtil.generateToken(userDetails);

        }

    }

}
