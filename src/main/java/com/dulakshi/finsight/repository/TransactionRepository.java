package com.dulakshi.finsight.repository;

import com.dulakshi.finsight.entity.Category;
import com.dulakshi.finsight.entity.Transaction;
import com.dulakshi.finsight.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    List<Transaction> findByUserId(Integer userId);

    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t " +
            "WHERE t.user.id = :userId AND t.category = :category AND t.transactionDate BETWEEN :startDate AND :endDate")
    Optional<BigDecimal> getTotalTransactions(
            @Param("userId") Integer userId,
            @Param("category") Category category,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t " +
            "WHERE t.user.id = :userId AND t.transactionDate BETWEEN :startDate AND :endDate")
    Optional<BigDecimal> getTotalTransactions(
            @Param("userId") Integer userId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t WHERE t.user.id = :userId AND t.transactionDate = :date")
    Optional<BigDecimal> getDailyTransactions(@Param("userId") Integer userId, @Param("date") LocalDate date);

    List<Transaction> findTransactionsByUserOrderByTransactionDateDesc(User user);

    Integer user(User user);
}