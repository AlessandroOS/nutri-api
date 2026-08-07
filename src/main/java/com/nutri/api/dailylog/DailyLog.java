package com.nutri.api.dailylog;

import com.nutri.api.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "daily_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailyLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private LocalDate date;

    @Column(name = "weight_kg")
    private BigDecimal weightKg;

    @Column(name = "total_calories", nullable = false)
    private BigDecimal totalCalories;

    @Column(name = "total_protein_g", nullable = false)
    private BigDecimal totalProteinG;

    @Column(name = "total_carb_g", nullable = false)
    private BigDecimal totalCarbG;

    @Column(name = "total_fat_g", nullable = false)
    private BigDecimal totalFatG;

    @PrePersist
    public void prePersist() {
        if (this.totalCalories == null) this.totalCalories = BigDecimal.ZERO;
        if (this.totalProteinG == null) this.totalProteinG = BigDecimal.ZERO;
        if (this.totalCarbG == null) this.totalCarbG = BigDecimal.ZERO;
        if (this.totalFatG == null) this.totalFatG = BigDecimal.ZERO;
    }
}