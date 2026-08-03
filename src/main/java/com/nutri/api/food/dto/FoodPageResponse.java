package com.nutri.api.food.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class FoodPageResponse {
    private List<FoodResponse> content;
    private int page;
    private int totalPages;
    private long totalElements;
}