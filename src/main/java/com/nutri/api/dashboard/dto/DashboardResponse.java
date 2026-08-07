package com.nutri.api.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class DashboardResponse {

    private LocalDate date;
    private Integer calorieGoal;
    private BigDecimal totalCalories;
    private BigDecimal totalProteinG;
    private BigDecimal totalCarbG;
    private BigDecimal totalFatG;
    private BigDecimal weightKg;
    private Double calorieProgressPercent;
}