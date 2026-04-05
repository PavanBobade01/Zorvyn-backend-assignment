package io.github.pavanbobade01.finance.security;

import io.github.pavanbobade01.finance.module.user.User;
import io.github.pavanbobade01.finance.module.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmailAndActiveTrue(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found or inactive"));

        return user; // ✅ Your User already implements UserDetails
    }
}