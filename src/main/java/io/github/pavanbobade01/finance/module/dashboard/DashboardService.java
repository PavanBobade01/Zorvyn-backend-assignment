package io.github.pavanbobade01.finance.module.dashboard;

import io.github.pavanbobade01.finance.module.dashboard.dto.CategoryTotalResponse;
import io.github.pavanbobade01.finance.module.dashboard.dto.MonthlyTrendResponse;
import io.github.pavanbobade01.finance.module.dashboard.dto.SummaryResponse;
import io.github.pavanbobade01.finance.module.transaction.TransactionRepository;
import io.github.pavanbobade01.finance.module.transaction.TransactionType;
import io.github.pavanbobade01.finance.module.transaction.dto.TransactionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final TransactionRepository transactionRepo;

    // ─────────────────────────────────────────
    // SUMMARY (Income, Expense, Balance)
    // ─────────────────────────────────────────

    public SummaryResponse getSummary() {

        Double totalIncome  = transactionRepo.sumByType(TransactionType.INCOME);
        Double totalExpense = transactionRepo.sumByType(TransactionType.EXPENSE);

        double income  = orZero(totalIncome);
        double expense = orZero(totalExpense);

        return SummaryResponse.builder()
                .totalIncome(income)
                .totalExpense(expense)
                .netBalance(income - expense)
                .build();
    }

    // ─────────────────────────────────────────
    // CATEGORY TOTALS
    // ─────────────────────────────────────────

    public List<CategoryTotalResponse> getCategoryTotals() {
        return transactionRepo.sumByCategory();
    }

    // ─────────────────────────────────────────
    // MONTHLY TRENDS
    // ─────────────────────────────────────────

    public List<MonthlyTrendResponse> getMonthlyTrends() {
        return transactionRepo.monthlyTrends();
    }

    // ─────────────────────────────────────────
    // 🔥 RECENT TRANSACTIONS (FIX ADDED)
    // ─────────────────────────────────────────

    public List<TransactionResponse> getRecentTransactions(Pageable pageable) {
        return transactionRepo.findByDeletedFalse(pageable)
                .map(TransactionResponse::from)
                .getContent();
    }

    // ─────────────────────────────────────────
    // NULL SAFETY
    // ─────────────────────────────────────────

    private double orZero(Double val) {
        return val != null ? val : 0.0;
    }
}