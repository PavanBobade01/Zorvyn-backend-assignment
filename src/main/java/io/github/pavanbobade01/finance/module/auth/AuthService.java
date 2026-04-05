package io.github.pavanbobade01.finance.module.auth;

import io.github.pavanbobade01.finance.module.auth.dto.*;
import io.github.pavanbobade01.finance.module.user.*;
import io.github.pavanbobade01.finance.security.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepo;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;

    // ─────────────────────────────────────────
    // REGISTER
    // ─────────────────────────────────────────

    public AuthResponse register(RegisterRequest req) {

        String email = req.getEmail().toLowerCase().trim();

        if (userRepo.existsByEmail(email)) {
            throw new RuntimeException("Email already in use");
        }

        User user = User.builder()
                .name(req.getName())
                .email(email)
                .password(encoder.encode(req.getPassword()))
                .role(Role.VIEWER)
                .active(true)
                .build();

        userRepo.save(user);

        log.info("User registered: {}", email);

        return new AuthResponse(jwtService.generateToken(user));
    }

    // ─────────────────────────────────────────
    // LOGIN
    // ─────────────────────────────────────────

    public AuthResponse login(LoginRequest req) {

        String email = req.getEmail().toLowerCase().trim();

        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, req.getPassword())
        );

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!user.isActive()) {
            throw new RuntimeException("Account is inactive");
        }

        log.info("User logged in: {}", email);

        return new AuthResponse(jwtService.generateToken(user));
    }
}