package com.nutri.api.dailylog;

import com.nutri.api.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DailyLogRepository extends JpaRepository<DailyLog, Long> {
    Optional<DailyLog> findByUserAndDate(User user, LocalDate date);
    List<DailyLog> findByUserAndDateBetweenOrderByDateAsc(User user, LocalDate start, LocalDate end);
}