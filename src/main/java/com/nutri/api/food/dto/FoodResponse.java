package com.nutri.api.food.dto;

import com.nutri.api.food.Food;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class FoodResponse {

    private Long id;
    private String name;
    private BigDecimal calories;
    private BigDecimal proteinG;
    private BigDecimal carbG;
    private BigDecimal fatG;
    private BigDecimal fiberG;

    public static FoodResponse from(Food food) {
        var response = new FoodResponse();
        response.setId(food.getId());
        response.setName(food.getName());
        response.setCalories(food.getCalories());
        response.setProteinG(food.getProteinG());
        response.setCarbG(food.getCarbG());
        response.setFatG(food.getFatG());
        response.setFiberG(food.getFiberG());
        return response;
    }
}