package com.nutri.api.meal.dto;

import com.nutri.api.meal.Meal;
import com.nutri.api.meal.MealItem;
import com.nutri.api.meal.MealType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class MealResponse {

    private Long id;
    private MealType mealType;
    private LocalDate date;
    private List<MealItemResponse> items;
    private BigDecimal totalCalories;
    private BigDecimal totalProteinG;
    private BigDecimal totalCarbG;
    private BigDecimal totalFatG;

    public static MealResponse from(Meal meal) {
        var response = new MealResponse();
        response.setId(meal.getId());
        response.setMealType(meal.getMealType());
        response.setDate(meal.getDate());
        response.setItems(meal.getItems().stream().map(MealItemResponse::from).toList());

        response.setTotalCalories(meal.getItems().stream()
                .map(MealItem::getCalories)
                .reduce(BigDecimal.ZERO, BigDecimal::add));
        response.setTotalProteinG(meal.getItems().stream()
                .map(MealItem::getProteinG)
                .reduce(BigDecimal.ZERO, BigDecimal::add));
        response.setTotalCarbG(meal.getItems().stream()
                .map(MealItem::getCarbG)
                .reduce(BigDecimal.ZERO, BigDecimal::add));
        response.setTotalFatG(meal.getItems().stream()
                .map(MealItem::getFatG)
                .reduce(BigDecimal.ZERO, BigDecimal::add));

        return response;
    }
}