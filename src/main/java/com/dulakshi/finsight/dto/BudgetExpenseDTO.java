package com.dulakshi.finsight.dto;

import lombok.Data;

@Data
public class BudgetExpenseDTO {
    private String categoryName;
    private Double budgetAmount;
    private Double totalSpentAmount;
}
