package com.nutri.api.dashboard.dto;

import com.nutri.api.dailylog.DailyLog;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class DailyLogResponse {

    private LocalDate date;
    private BigDecimal weightKg;
    private BigDecimal totalCalories;
    private BigDecimal totalProteinG;
    private BigDecimal totalCarbG;
    private BigDecimal totalFatG;

    public static DailyLogResponse from(DailyLog log) {
        var response = new DailyLogResponse();
        response.setDate(log.getDate());
        response.setWeightKg(log.getWeightKg());
        response.setTotalCalories(log.getTotalCalories());
        response.setTotalProteinG(log.getTotalProteinG());
        response.setTotalCarbG(log.getTotalCarbG());
        response.setTotalFatG(log.getTotalFatG());
        return response;
    }
}