package io.github.pavanbobade01.finance.module.auth;

import io.github.pavanbobade01.finance.module.auth.dto.*;
import io.github.pavanbobade01.finance.util.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    private final AuthService authService;

    // ─────────────────────────────────────────
    // REGISTER
    // ─────────────────────────────────────────

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(
            @Valid @RequestBody RegisterRequest req) {

        log.info("Register request for email: {}", req.getEmail());

        return ResponseEntity.status(201)
                .body(ApiResponse.success(
                        "User registered successfully",
                        authService.register(req)
                ));
    }

    // ─────────────────────────────────────────
    // LOGIN
    // ─────────────────────────────────────────

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(
            @Valid @RequestBody LoginRequest req) {

        log.info("Login attempt for email: {}", req.getEmail());

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Login successful",
                        authService.login(req)
                )
        );
    }
}