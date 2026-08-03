package com.nutri.api.meal;

import com.nutri.api.food.FoodRepository;
import com.nutri.api.meal.dto.*;
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
public class MealService {

    private final MealRepository mealRepository;
    private final FoodRepository foodRepository;

    public List<MealResponse> findByDate(User user, LocalDate date) {
        return mealRepository.findByUserAndDateOrderByMealTypeAsc(user, date)
                .stream()
                .map(MealResponse::from)
                .toList();
    }

    @Transactional
    public MealResponse create(User user, MealRequest request) {
        var meal = Meal.builder()
                .user(user)
                .mealType(request.getMealType())
                .date(request.getDate())
                .build();

        return MealResponse.from(mealRepository.save(meal));
    }

    @Transactional
    public MealResponse addItem(User user, Long mealId, MealItemRequest request) {
        var meal = mealRepository.findByIdAndUser(mealId, user)
                .orElseThrow(() -> new IllegalArgumentException("Refeição não encontrada"));

        var food = foodRepository.findById(request.getFoodId())
                .orElseThrow(() -> new IllegalArgumentException("Alimento não encontrado"));

        // Calcula macros com base na quantidade informada
        var factor = request.getQuantityG()
                .divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP);

        var item = MealItem.builder()
                .meal(meal)
                .food(food)
                .quantityG(request.getQuantityG())
                .calories(food.getCalories().multiply(factor).setScale(2, RoundingMode.HALF_UP))
                .proteinG(food.getProteinG().multiply(factor).setScale(2, RoundingMode.HALF_UP))
                .carbG(food.getCarbG().multiply(factor).setScale(2, RoundingMode.HALF_UP))
                .fatG(food.getFatG().multiply(factor).setScale(2, RoundingMode.HALF_UP))
                .build();

        meal.getItems().add(item);

        return MealResponse.from(mealRepository.save(meal));
    }

    @Transactional
    public MealResponse removeItem(User user, Long mealId, Long itemId) {
        var meal = mealRepository.findByIdAndUser(mealId, user)
                .orElseThrow(() -> new IllegalArgumentException("Refeição não encontrada"));

        meal.getItems().removeIf(item -> item.getId().equals(itemId));

        return MealResponse.from(mealRepository.save(meal));
    }

    @Transactional
    public void delete(User user, Long mealId) {
        var meal = mealRepository.findByIdAndUser(mealId, user)
                .orElseThrow(() -> new IllegalArgumentException("Refeição não encontrada"));

        mealRepository.delete(meal);
    }
}