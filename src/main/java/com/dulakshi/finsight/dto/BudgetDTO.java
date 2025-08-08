package com.dulakshi.finsight.dto;

import com.dulakshi.finsight.entity.Category;
import lombok.Data;

@Data
public class BudgetDTO {
    private Integer id;
    private String period;
    private Double budgetAmount;
    private Double spentAmount;
    private Category category;
}
