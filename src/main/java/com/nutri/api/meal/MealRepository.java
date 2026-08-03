package com.nutri.api.meal;

import com.nutri.api.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MealRepository extends JpaRepository<Meal, Long> {
    List<Meal> findByUserAndDateOrderByMealTypeAsc(User user, LocalDate date);
    Optional<Meal> findByIdAndUser(Long id, User user);
}