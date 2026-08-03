package com.nutri.api.food;

import com.nutri.api.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "foods")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal calories;

    @Column(name = "protein_g", nullable = false)
    private BigDecimal proteinG;

    @Column(name = "carb_g", nullable = false)
    private BigDecimal carbG;

    @Column(name = "fat_g", nullable = false)
    private BigDecimal fatG;

    @Column(name = "fiber_g")
    private BigDecimal fiberG;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User createdBy;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}