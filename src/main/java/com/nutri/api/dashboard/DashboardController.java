package com.nutri.api.dashboard;

import com.nutri.api.dashboard.dto.DailyLogResponse;
import com.nutri.api.dashboard.dto.DashboardResponse;
import com.nutri.api.dashboard.dto.WeightRequest;
import com.nutri.api.user.User;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/today")
    public ResponseEntity<DashboardResponse> getToday(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(dashboardService.getToday(user));
    }

    @GetMapping("/history")
    public ResponseEntity<List<DailyLogResponse>> getHistory(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(dashboardService.getHistory(user));
    }

    @PostMapping("/weight")
    public ResponseEntity<DashboardResponse> registerWeight(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody WeightRequest request) {
        return ResponseEntity.ok(dashboardService.registerWeight(user, request));
    }
}