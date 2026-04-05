package io.github.pavanbobade01.finance.module.transaction;

import io.github.pavanbobade01.finance.module.transaction.dto.TransactionRequest;
import io.github.pavanbobade01.finance.module.user.User;
import io.github.pavanbobade01.finance.util.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    // ─────────────────────────────────────────
    // CREATE TRANSACTION
    // ─────────────────────────────────────────

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','ANALYST')")
    public ResponseEntity<ApiResponse<?>> create(
            @Valid @RequestBody TransactionRequest req,
            @AuthenticationPrincipal User user) {

        return ResponseEntity.status(201)
                .body(ApiResponse.success(transactionService.create(req, user)));
    }

    // ─────────────────────────────────────────
    // GET ALL WITH FILTERS
    // ─────────────────────────────────────────

    @GetMapping
    @PreAuthorize("hasAnyRole('VIEWER','ANALYST','ADMIN')")
    public ResponseEntity<ApiResponse<?>> getAll(
            @RequestParam(required = false) TransactionType type,
            @RequestParam(required = false) String category,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        return ResponseEntity.ok(ApiResponse.success(
                transactionService.getAll(type, category, from, to, pageable)));
    }

    // ─────────────────────────────────────────
    // GET BY ID
    // ─────────────────────────────────────────

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('VIEWER','ANALYST','ADMIN')")
    public ResponseEntity<ApiResponse<?>> getById(@PathVariable String id) {
        return ResponseEntity.ok(ApiResponse.success(transactionService.getById(id)));
    }

    // ─────────────────────────────────────────
    // UPDATE
    // ─────────────────────────────────────────

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<?>> update(
            @PathVariable String id,
            @Valid @RequestBody TransactionRequest req) {

        return ResponseEntity.ok(ApiResponse.success(transactionService.update(id, req)));
    }

    // ─────────────────────────────────────────
    // DELETE (SOFT DELETE)
    // ─────────────────────────────────────────

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<?>> delete(@PathVariable String id) {

        transactionService.softDelete(id);

        return ResponseEntity.ok(ApiResponse.success("Transaction deleted successfully"));
    }
}