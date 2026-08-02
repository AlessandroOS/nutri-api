package com.nutri.api.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UpdateGoalRequest {

    @NotBlank(message = "Objetivo é obrigatório")
    @Pattern(regexp = "^(LOSS|MAINTENANCE|GAIN)$",
            message = "Objetivo deve ser LOSS, MAINTENANCE ou GAIN")
    private String goal;
}