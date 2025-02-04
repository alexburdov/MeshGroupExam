package ru.alex.burdovitsin.mesh.config.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.alex.burdovitsin.mesh.model.jpa.User;
import ru.alex.burdovitsin.mesh.services.UserService;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private static final String USER_NAME_NOT_FOUND_MESSAGE = "User with name %s not found";

    private final UserService userService;

    public JwtUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getByUsername(username);
        return new JwtUserDetails(user);
    }
}
