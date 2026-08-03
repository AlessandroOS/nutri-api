package com.nutri.api.food;

import com.nutri.api.food.dto.FoodPageResponse;
import com.nutri.api.food.dto.FoodRequest;
import com.nutri.api.food.dto.FoodResponse;
import com.nutri.api.user.User;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/foods")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class FoodController {

    private final FoodService foodService;

    @GetMapping
    public ResponseEntity<FoodPageResponse> search(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(foodService.search(name, page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FoodResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(foodService.findById(id));
    }

    @PostMapping
    public ResponseEntity<FoodResponse> create(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody FoodRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(foodService.create(request, user));
    }
}