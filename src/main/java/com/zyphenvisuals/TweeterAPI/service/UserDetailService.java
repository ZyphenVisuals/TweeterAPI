package com.zyphenvisuals.TweeterAPI.service;

import com.zyphenvisuals.TweeterAPI.model.UserPrincipal;
import com.zyphenvisuals.TweeterAPI.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        return UserPrincipal.builder()
                .id(user.getId())
                .authorities(List.of(new SimpleGrantedAuthority("USER")))
                .password(user.getPassword())
                .build();
    }
}
