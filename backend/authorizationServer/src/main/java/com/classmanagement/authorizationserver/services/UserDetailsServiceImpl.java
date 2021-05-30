package com.classmanagement.authorizationserver.services;

import com.classmanagement.authorizationserver.configurations.authDto.AuthUser;
import com.classmanagement.authorizationserver.entities.User;
import com.classmanagement.authorizationserver.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@AllArgsConstructor
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) return null;
        return new AuthUser(
                user.getEmail(),
                user.getPassword(),
                user.isEnabled(),
                true,
                true,
                !user.isDefaultPwd() && user.isEmailVerified(),
                Collections.singleton(user.getRole()),
                user.isDefaultPwd()
        );
    }
}
