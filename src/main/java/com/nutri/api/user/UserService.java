package com.nutri.api.user;

import com.nutri.api.user.dto.UpdateGoalRequest;
import com.nutri.api.user.dto.UpdateProfileRequest;
import com.nutri.api.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponse getProfile(User user) {
        return UserResponse.from(user, calculateBmr(user));
    }

    public UserResponse updateProfile(User user, UpdateProfileRequest request) {
        if (request.getBirthDate() != null) user.setBirthDate(request.getBirthDate());
        if (request.getSex() != null) user.setSex(request.getSex());
        if (request.getHeightCm() != null) user.setHeightCm(request.getHeightCm());
        if (request.getWeightKg() != null) user.setWeightKg(request.getWeightKg());

        userRepository.save(user);
        return UserResponse.from(user, calculateBmr(user));
    }

    public UserResponse updateGoal(User user, UpdateGoalRequest request) {
        user.setGoal(request.getGoal());
        user.setCalorieGoal(calculateCalorieGoal(user, request.getGoal()));

        userRepository.save(user);
        return UserResponse.from(user, calculateBmr(user));
    }

    private Double calculateBmr(User user) {
        if (user.getWeightKg() == null || user.getHeightCm() == null
                || user.getBirthDate() == null || user.getSex() == null) {
            return null;
        }

        int age = java.time.LocalDate.now().getYear() - user.getBirthDate().getYear();
        double weight = user.getWeightKg().doubleValue();
        double height = user.getHeightCm().doubleValue();

        // Fórmula de Mifflin-St Jeor
        if ("M".equals(user.getSex())) {
            return (10 * weight) + (6.25 * height) - (5 * age) + 5;
        } else {
            return (10 * weight) + (6.25 * height) - (5 * age) - 161;
        }
    }

    private Integer calculateCalorieGoal(User user, String goal) {
        Double bmr = calculateBmr(user);
        if (bmr == null) return null;

        // Fator de atividade moderada (1.55)
        double tdee = bmr * 1.55;

        return switch (goal) {
            case "LOSS"        -> (int) (tdee - 500);
            case "GAIN"        -> (int) (tdee + 300);
            default            -> (int) tdee;       // MAINTENANCE
        };
    }
}