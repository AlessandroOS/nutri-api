package com.nutri.api.user.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class UpdateProfileRequest {

    @Past(message = "Data de nascimento deve ser no passado")
    private LocalDate birthDate;

    @Pattern(regexp = "^[MF]$", message = "Sexo deve ser M ou F")
    private String sex;

    @DecimalMin(value = "100.0", message = "Altura deve ser no mínimo 100cm")
    @DecimalMax(value = "250.0", message = "Altura deve ser no máximo 250cm")
    private BigDecimal heightCm;

    @DecimalMin(value = "30.0", message = "Peso deve ser no mínimo 30kg")
    @DecimalMax(value = "300.0", message = "Peso deve ser no máximo 300kg")
    private BigDecimal weightKg;
}