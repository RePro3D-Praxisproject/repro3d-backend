package org.repro3d.service;

import org.repro3d.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.repro3d.model.User;

import java.util.Optional;

/**
 * UserDetailsServiceImpl implements the UserDetailsService interface to provide user details for authentication.
 * It loads user-specific data given a username (email).
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {


    @Autowired
    private UserRepository userRepository;

    /**
     * Loads the user's details by their email.
     *
     * @param email The email of the user to load.
     * @return The UserDetails of the user.
     * @throws UsernameNotFoundException if the user with the specified email is not found.
     */
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