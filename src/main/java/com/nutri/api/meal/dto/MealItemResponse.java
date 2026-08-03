package com.nutri.api.meal.dto;

import com.nutri.api.meal.MealItem;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class MealItemResponse {

    private Long id;
    private Long foodId;
    private String foodName;
    private BigDecimal quantityG;
    private BigDecimal calories;
    private BigDecimal proteinG;
    private BigDecimal carbG;
    private BigDecimal fatG;

    public static MealItemResponse from(MealItem item) {
        var response = new MealItemResponse();
        response.setId(item.getId());
        response.setFoodId(item.getFood().getId());
        response.setFoodName(item.getFood().getName());
        response.setQuantityG(item.getQuantityG());
        response.setCalories(item.getCalories());
        response.setProteinG(item.getProteinG());
        response.setCarbG(item.getCarbG());
        response.setFatG(item.getFatG());
        return response;
    }
}