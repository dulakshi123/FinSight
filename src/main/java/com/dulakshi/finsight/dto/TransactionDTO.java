package com.dulakshi.finsight.dto;

import com.dulakshi.finsight.entity.Category;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TransactionDTO {
    private Integer id;
    private LocalDate date;
    private String description;
    private Double amount;
    private Category category;
}
