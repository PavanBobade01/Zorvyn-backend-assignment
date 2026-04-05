package io.github.pavanbobade01.finance.module.dashboard.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SummaryResponse {

    private double totalIncome;
    private double totalExpense;
    private double netBalance;
}