package com.nutri.api.meal.dto;

import com.nutri.api.meal.MealType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MealRequest {

    @NotNull(message = "Tipo de refeição é obrigatório")
    private MealType mealType;

    @NotNull(message = "Data é obrigatória")
    private LocalDate date;
}