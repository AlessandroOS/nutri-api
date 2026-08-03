package com.nutri.api.meal;

import com.nutri.api.meal.dto.*;
import com.nutri.api.user.User;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/meals")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class MealController {

    private final MealService mealService;

    @GetMapping
    public ResponseEntity<List<MealResponse>> findByDate(
            @AuthenticationPrincipal User user,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(mealService.findByDate(user, date));
    }

    @PostMapping
    public ResponseEntity<MealResponse> create(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody MealRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mealService.create(user, request));
    }

    @PostMapping("/{mealId}/items")
    public ResponseEntity<MealResponse> addItem(
            @AuthenticationPrincipal User user,
            @PathVariable Long mealId,
            @Valid @RequestBody MealItemRequest request) {
        return ResponseEntity.ok(mealService.addItem(user, mealId, request));
    }

    @DeleteMapping("/{mealId}/items/{itemId}")
    public ResponseEntity<MealResponse> removeItem(
            @AuthenticationPrincipal User user,
            @PathVariable Long mealId,
            @PathVariable Long itemId) {
        return ResponseEntity.ok(mealService.removeItem(user, mealId, itemId));
    }

    @DeleteMapping("/{mealId}")
    public ResponseEntity<Void> delete(
            @AuthenticationPrincipal User user,
            @PathVariable Long mealId) {
        mealService.delete(user, mealId);
        return ResponseEntity.noContent().build();
    }
}