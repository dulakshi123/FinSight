package com.dulakshi.finsight.repository;

import com.dulakshi.finsight.entity.Budget;
import com.dulakshi.finsight.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BudgetRepository extends JpaRepository<Budget, Integer> {
    List<Budget> findByUserId(Integer userId);

    @Query("SELECT b.budgetAmount FROM Budget b WHERE b.user.id = :userId AND b.category = :category " +
            "AND b.startDate = :startDate AND b.endDate  = :endDate")
    Optional<BigDecimal> findBudgetAmount(
            @Param("userId") Integer userId,
            @Param("category") Category category,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    @Query("SELECT COALESCE(SUM(b.budgetAmount), 0) FROM Budget b WHERE b.user.id = :userId " +
            "AND b.startDate = :startDate AND b.endDate  = :endDate")
    Optional<BigDecimal> findBudgetAmount(
            @Param("userId") Integer userId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );
}