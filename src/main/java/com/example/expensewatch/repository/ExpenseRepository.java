package com.example.expensewatch.repository;

import com.example.expensewatch.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByExpenseDateBetweenAndUserId(LocalDate start, LocalDate end, Long userId);

    @Query("SELECT e.category as cat, SUM(e.amount) as total FROM Expense e WHERE e.expenseDate BETWEEN :start AND :end AND e.user.id = :userId GROUP BY e.category")
    List<Object[]> totalByCategoryBetween(@Param("start") LocalDate start, @Param("end") LocalDate end, @Param("userId") Long userId);

    @Query("SELECT FUNCTION('DATE_FORMAT', e.expenseDate, '%Y-%m') as m, SUM(e.amount) as total FROM Expense e WHERE e.expenseDate BETWEEN :start AND :end AND e.user.id = :userId GROUP BY FUNCTION('DATE_FORMAT', e.expenseDate, '%Y-%m') ORDER BY m")
    List<Object[]> totalByMonthBetween(@Param("start") LocalDate start, @Param("end") LocalDate end, @Param("userId") Long userId);
}
