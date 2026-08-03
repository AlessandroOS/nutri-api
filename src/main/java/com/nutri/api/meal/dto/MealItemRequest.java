package com.nutri.api.meal.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class MealItemRequest {

    @NotNull(message = "Alimento é obrigatório")
    private Long foodId;

    @NotNull(message = "Quantidade é obrigatória")
    @DecimalMin(value = "0.1", message = "Quantidade deve ser maior que zero")
    private BigDecimal quantityG;
}