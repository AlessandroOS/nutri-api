package com.nutri.api.meal;

import com.nutri.api.food.Food;
import com.nutri.api.food.FoodRepository;
import com.nutri.api.meal.dto.MealItemRequest;
import com.nutri.api.meal.dto.MealRequest;
import com.nutri.api.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MealServiceTest {

    @Mock
    private MealRepository mealRepository;

    @Mock
    private FoodRepository foodRepository;

    @InjectMocks
    private MealService mealService;

    @Test
    void create_shouldReturnMealResponse() {
        var user = User.builder().id(1L).email("joao@email.com").build();
        var request = new MealRequest();
        request.setMealType(MealType.LUNCH);
        request.setDate(LocalDate.now());

        var meal = Meal.builder()
                .id(1L)
                .user(user)
                .mealType(MealType.LUNCH)
                .date(LocalDate.now())
                .build();

        when(mealRepository.save(any(Meal.class))).thenReturn(meal);

        var response = mealService.create(user, request);

        assertNotNull(response);
        assertEquals(MealType.LUNCH, response.getMealType());
        verify(mealRepository).save(any(Meal.class));
    }

    @Test
    void addItem_shouldCalculateMacrosCorrectly() {
        var user = User.builder().id(1L).build();

        var food = Food.builder()
                .id(1L)
                .name("Frango grelhado")
                .calories(new BigDecimal("165"))
                .proteinG(new BigDecimal("31"))
                .carbG(new BigDecimal("0"))
                .fatG(new BigDecimal("3.6"))
                .build();

        var meal = Meal.builder()
                .id(1L)
                .user(user)
                .mealType(MealType.LUNCH)
                .date(LocalDate.now())
                .build();

        var request = new MealItemRequest();
        request.setFoodId(1L);
        request.setQuantityG(new BigDecimal("150"));

        when(mealRepository.findByIdAndUser(1L, user)).thenReturn(Optional.of(meal));
        when(foodRepository.findById(1L)).thenReturn(Optional.of(food));
        when(mealRepository.save(any(Meal.class))).thenAnswer(i -> i.getArgument(0));

        var response = mealService.addItem(user, 1L, request);

        assertEquals(1, response.getItems().size());
        // 150g de frango: 165 * 1.5 = 247.50 kcal
        assertEquals(new BigDecimal("247.50"), response.getItems().get(0).getCalories());
        // 31 * 1.5 = 46.50g proteína
        assertEquals(new BigDecimal("46.50"), response.getItems().get(0).getProteinG());
    }

    @Test
    void addItem_shouldThrowException_whenMealNotFound() {
        var user = User.builder().id(1L).build();
        var request = new MealItemRequest();
        request.setFoodId(1L);
        request.setQuantityG(new BigDecimal("100"));

        when(mealRepository.findByIdAndUser(99L, user)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
                () -> mealService.addItem(user, 99L, request));
    }
}