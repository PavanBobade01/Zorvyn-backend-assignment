package io.github.pavanbobade01.finance.module.dashboard.dto;

public interface MonthlyTrendResponse {

    Integer getMonth();

    String getType();

    Double getTotal();
}