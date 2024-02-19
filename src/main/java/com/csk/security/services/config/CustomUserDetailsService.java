package com.csk.security.services.config;

import com.csk.security.services.entity.Authority;
import com.csk.security.services.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String userName;
        String password;

        var user = userRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User doesn't exist"));

        userName = user.getUserName();
        password = user.getPwd();
        var authorities = convertUserRolesToAuthorities(user.getAuthorities());

        return new User(userName, password, authorities);
    }

    private List<SimpleGrantedAuthority> convertUserRolesToAuthorities(List<Authority> authorities) {

        return authorities.stream()
                .map(Authority::getAuthority)
                .map(SimpleGrantedAuthority::new)
                .toList();
    }
}
