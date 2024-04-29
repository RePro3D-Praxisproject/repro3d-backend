package org.repro3d.service;

import org.repro3d.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.repro3d.model.User;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {


    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<User> userDetails = userRepository.findByEmail(email);

        if (userDetails.isEmpty()) {
            throw new UsernameNotFoundException("User with username: " + email + " not found");
        }



        return org.springframework.security.core.userdetails.User.builder()
                .username(userDetails.get().getEmail())
                .password(userDetails.get().getPasswordHash())
                .build();
    }
}