package io.github.pavanbobade01.finance.module.transaction;

import io.github.pavanbobade01.finance.module.dashboard.dto.CategoryTotalResponse;
import io.github.pavanbobade01.finance.module.dashboard.dto.MonthlyTrendResponse;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, String> {

    // ─────────────────────────────────────────
    // Basic Queries (Soft Delete Aware)
    // ─────────────────────────────────────────

    Page<Transaction> findByDeletedFalse(Pageable pageable);

    Page<Transaction> findByUserIdAndDeletedFalse(String userId, Pageable pageable);

    Page<Transaction> findByDeletedFalseAndType(TransactionType type, Pageable pageable);

    Page<Transaction> findByDeletedFalseAndCategory(String category, Pageable pageable);

    Page<Transaction> findByDeletedFalseAndDateBetween(
            LocalDate start,
            LocalDate end,
            Pageable pageable
    );

    // ─────────────────────────────────────────
    // Dashboard Aggregations
    // ─────────────────────────────────────────

    @Query("""
        SELECT SUM(t.amount)
        FROM Transaction t
        WHERE t.type = :type AND t.deleted = false
    """)
    Double sumByType(@Param("type") TransactionType type);

    // Category-wise totals
    @Query("""
        SELECT t.category AS category, SUM(t.amount) AS total
        FROM Transaction t
        WHERE t.deleted = false
        GROUP BY t.category
    """)
    List<CategoryTotalResponse> sumByCategory();

    // Monthly trends (PostgreSQL optimized)
    @Query("""
        SELECT EXTRACT(MONTH FROM t.date) AS month,
               t.type AS type,
               SUM(t.amount) AS total
        FROM Transaction t
        WHERE t.deleted = false
        GROUP BY EXTRACT(MONTH FROM t.date), t.type
        ORDER BY month ASC
    """)
    List<MonthlyTrendResponse> monthlyTrends();
}