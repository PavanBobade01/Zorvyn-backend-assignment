package io.github.pavanbobade01.finance.exception;

import io.github.pavanbobade01.finance.util.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // ─────────────────────────────────────────
    // VALIDATION ERRORS
    // ─────────────────────────────────────────

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleValidation(MethodArgumentNotValidException ex) {

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .toList();

        return ResponseEntity.badRequest()
                .body(ApiResponse.error(errors));
    }

    // ─────────────────────────────────────────
    // NOT FOUND
    // ─────────────────────────────────────────

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(404)
                .body(ApiResponse.error(ex.getMessage()));
    }

    // ─────────────────────────────────────────
    // FORBIDDEN
    // ─────────────────────────────────────────

    @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
    public ResponseEntity<ApiResponse<?>> handleForbidden(Exception ex) {
        return ResponseEntity.status(403)
                .body(ApiResponse.error("Access denied"));
    }

    // ─────────────────────────────────────────
    // CONFLICT
    // ─────────────────────────────────────────

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ApiResponse<?>> handleConflict(ConflictException ex) {
        return ResponseEntity.status(409)
                .body(ApiResponse.error(ex.getMessage()));
    }

    // ─────────────────────────────────────────
    // UNAUTHORIZED (LOGIN FAIL)
    // ─────────────────────────────────────────

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse<?>> handleBadCredentials(Exception ex) {
        return ResponseEntity.status(401)
                .body(ApiResponse.error("Invalid credentials"));
    }

    // ─────────────────────────────────────────
    // GENERAL ERROR
    // ─────────────────────────────────────────

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleGeneral(Exception ex) {

        log.error("Unexpected error occurred", ex);

        return ResponseEntity.status(500)
                .body(ApiResponse.error("Internal server error"));
    }
}