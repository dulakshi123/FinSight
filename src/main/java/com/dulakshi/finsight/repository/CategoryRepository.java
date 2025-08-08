package com.dulakshi.finsight.repository;

import com.dulakshi.finsight.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Optional<Category> findCategoryByName(String name);
}