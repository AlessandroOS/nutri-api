package com.nutri.api.dashboard;

import com.nutri.api.dailylog.DailyLog;
import com.nutri.api.dailylog.DailyLogRepository;
import com.nutri.api.dashboard.dto.DailyLogResponse;
import com.nutri.api.dashboard.dto.DashboardResponse;
import com.nutri.api.dashboard.dto.WeightRequest;
import com.nutri.api.meal.MealRepository;
import com.nutri.api.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final DailyLogRepository dailyLogRepository;
    private final MealRepository mealRepository;

    public DashboardResponse getToday(User user) {
        var today = LocalDate.now();
        var log = getOrCreateLog(user, today);
        syncWithMeals(user, today, log);
        return buildDashboardResponse(user, log);
    }

    public List<DailyLogResponse> getHistory(User user) {
        var end = LocalDate.now();
        var start = end.minusDays(29);
        return dailyLogRepository
                .findByUserAndDateBetweenOrderByDateAsc(user, start, end)
                .stream()
                .map(DailyLogResponse::from)
                .toList();
    }

    @Transactional
    public DashboardResponse registerWeight(User user, WeightRequest request) {
        var today = LocalDate.now();
        var log = getOrCreateLog(user, today);
        log.setWeightKg(request.getWeightKg());
        dailyLogRepository.save(log);
        syncWithMeals(user, today, log);
        return buildDashboardResponse(user, log);
    }

    private void syncWithMeals(User user, LocalDate date, DailyLog log) {
        var meals = mealRepository.findByUserAndDateOrderByMealTypeAsc(user, date);

        var totalCalories = BigDecimal.ZERO;
        var totalProtein = BigDecimal.ZERO;
        var totalCarb = BigDecimal.ZERO;
        var totalFat = BigDecimal.ZERO;

        for (var meal : meals) {
            for (var item : meal.getItems()) {
                totalCalories = totalCalories.add(item.getCalories());
                totalProtein = totalProtein.add(item.getProteinG());
                totalCarb = totalCarb.add(item.getCarbG());
                totalFat = totalFat.add(item.getFatG());
            }
        }

        log.setTotalCalories(totalCalories);
        log.setTotalProteinG(totalProtein);
        log.setTotalCarbG(totalCarb);
        log.setTotalFatG(totalFat);

        dailyLogRepository.save(log);
    }

    private DailyLog getOrCreateLog(User user, LocalDate date) {
        return dailyLogRepository.findByUserAndDate(user, date)
                .orElseGet(() -> dailyLogRepository.save(
                        DailyLog.builder()
                                .user(user)
                                .date(date)
                                .totalCalories(BigDecimal.ZERO)
                                .totalProteinG(BigDecimal.ZERO)
                                .totalCarbG(BigDecimal.ZERO)
                                .totalFatG(BigDecimal.ZERO)
                                .build()
                ));
    }

    private DashboardResponse buildDashboardResponse(User user, DailyLog log) {
        Double progress = null;
        if (user.getCalorieGoal() != null && user.getCalorieGoal() > 0) {
            progress = log.getTotalCalories()
                    .divide(BigDecimal.valueOf(user.getCalorieGoal()), 4, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100))
                    .setScale(1, RoundingMode.HALF_UP)
                    .doubleValue();
        }

        return new DashboardResponse(
                log.getDate(),
                user.getCalorieGoal(),
                log.getTotalCalories(),
                log.getTotalProteinG(),
                log.getTotalCarbG(),
                log.getTotalFatG(),
                log.getWeightKg(),
                progress
        );
    }
}