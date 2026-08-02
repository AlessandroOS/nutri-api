package com.nutri.api.user.dto;

import com.nutri.api.user.User;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class UserResponse {

    private Long id;
    private String name;
    private String email;
    private LocalDate birthDate;
    private String sex;
    private BigDecimal heightCm;
    private BigDecimal weightKg;
    private String goal;
    private Integer calorieGoal;
    private Double bmr;

    public static UserResponse from(User user, Double bmr) {
        var response = new UserResponse();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setBirthDate(user.getBirthDate());
        response.setSex(user.getSex());
        response.setHeightCm(user.getHeightCm());
        response.setWeightKg(user.getWeightKg());
        response.setGoal(user.getGoal());
        response.setCalorieGoal(user.getCalorieGoal());
        response.setBmr(bmr);
        return response;
    }
}