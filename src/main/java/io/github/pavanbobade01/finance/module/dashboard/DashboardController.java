package io.github.pavanbobade01.finance.module.dashboard;

import io.github.pavanbobade01.finance.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    // ─────────────────────────────────────────
    // SUMMARY
    // ─────────────────────────────────────────

    @GetMapping("/summary")
    @PreAuthorize("hasAnyRole('ANALYST','ADMIN')")
    public ResponseEntity<ApiResponse<?>> summary() {
        return ResponseEntity.ok(
                ApiResponse.success(dashboardService.getSummary())
        );
    }

    // ─────────────────────────────────────────
    // CATEGORY TOTALS
    // ─────────────────────────────────────────

    @GetMapping("/by-category")
    @PreAuthorize("hasAnyRole('ANALYST','ADMIN')")
    public ResponseEntity<ApiResponse<?>> byCategory() {
        return ResponseEntity.ok(
                ApiResponse.success(dashboardService.getCategoryTotals())
        );
    }

    // ─────────────────────────────────────────
    // MONTHLY TRENDS
    // ─────────────────────────────────────────

    @GetMapping("/trends")
    @PreAuthorize("hasAnyRole('ANALYST','ADMIN')")
    public ResponseEntity<ApiResponse<?>> trends() {
        return ResponseEntity.ok(
                ApiResponse.success(dashboardService.getMonthlyTrends())
        );
    }

    // ─────────────────────────────────────────
    // RECENT TRANSACTIONS
    // ─────────────────────────────────────────

    @GetMapping("/recent")
    @PreAuthorize("hasAnyRole('VIEWER','ANALYST','ADMIN')")
    public ResponseEntity<ApiResponse<?>> recent() {

        Pageable pageable = PageRequest.of(
                0,
                5,
                Sort.by("createdAt").descending()
        );

        return ResponseEntity.ok(
                ApiResponse.success(dashboardService.getRecentTransactions(pageable))
        );
    }
}