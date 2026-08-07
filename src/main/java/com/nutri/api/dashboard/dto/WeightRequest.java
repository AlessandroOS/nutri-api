package com.nutri.api.dashboard.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class WeightRequest {

    @NotNull(message = "Peso é obrigatório")
    @DecimalMin(value = "30.0", message = "Peso deve ser maior que 30kg")
    private BigDecimal weightKg;
}