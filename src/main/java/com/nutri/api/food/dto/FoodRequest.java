package com.nutri.api.food.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class FoodRequest {

    @NotBlank(message = "Nome é obrigatório")
    private String name;

    @NotNull(message = "Calorias é obrigatório")
    @DecimalMin(value = "0.0", message = "Calorias deve ser maior ou igual a zero")
    private BigDecimal calories;

    @NotNull(message = "Proteína é obrigatório")
    @DecimalMin(value = "0.0", message = "Proteína deve ser maior ou igual a zero")
    private BigDecimal proteinG;

    @NotNull(message = "Carboidrato é obrigatório")
    @DecimalMin(value = "0.0", message = "Carboidrato deve ser maior ou igual a zero")
    private BigDecimal carbG;

    @NotNull(message = "Gordura é obrigatório")
    @DecimalMin(value = "0.0", message = "Gordura deve ser maior ou igual a zero")
    private BigDecimal fatG;

    @DecimalMin(value = "0.0", message = "Fibra deve ser maior ou igual a zero")
    private BigDecimal fiberG;
}