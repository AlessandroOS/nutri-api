package com.nutri.api.food;

import com.nutri.api.food.dto.FoodPageResponse;
import com.nutri.api.food.dto.FoodRequest;
import com.nutri.api.food.dto.FoodResponse;
import com.nutri.api.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FoodService {

    private final FoodRepository foodRepository;

    public FoodPageResponse search(String name, int page, int size) {
        var pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        var result = name != null && !name.isBlank()
                ? foodRepository.findByNameContainingIgnoreCase(name, pageable)
                : foodRepository.findAll(pageable);

        return new FoodPageResponse(
                result.getContent().stream().map(FoodResponse::from).toList(),
                result.getNumber(),
                result.getTotalPages(),
                result.getTotalElements()
        );
    }

    public FoodResponse findById(Long id) {
        var food = foodRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Alimento não encontrado"));
        return FoodResponse.from(food);
    }

    public FoodResponse create(FoodRequest request, User user) {
        var food = Food.builder()
                .name(request.getName())
                .calories(request.getCalories())
                .proteinG(request.getProteinG())
                .carbG(request.getCarbG())
                .fatG(request.getFatG())
                .fiberG(request.getFiberG())
                .createdBy(user)
                .build();

        return FoodResponse.from(foodRepository.save(food));
    }
}