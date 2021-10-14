package com.alkemy.disneyapi.user;

import com.alkemy.disneyapi.exception.EmailAlreadyInUseException;
import com.alkemy.disneyapi.mapstruct.dtos.UserDto;
import com.alkemy.disneyapi.security.CustomUserDetailsService;
import com.alkemy.disneyapi.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService{

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;

    private void checkEmailAvailability(String email) {

        if (userRepository.existsByEmail(email)) {

            throw new EmailAlreadyInUseException(email + " is already in use by another user");

        }

    }

    private Authentication authenticate(UserDto user) {

        Authentication authentication;

        try {

            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));

        } catch (AuthenticationException e) {

            throw new BadCredentialsException("Incorrect email and/or password");

        }

        return authentication;

    }

    public void saveUser(UserDto userToSave) {

        checkEmailAvailability(userToSave.getEmail());

        User user = new User();
        user.setEmail(userToSave.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(userToSave.getPassword()));

        userRepository.save(user);

    }

    public String logInUser(UserDto user) {

        Authentication authentication = authenticate(user);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(user.getEmail());

        return jwtUtil.generateToken(userDetails);

    }

}
