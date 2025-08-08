package com.dulakshi.finsight.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DailyTransactionDTO {
    private LocalDate date;
    private Double totalAmount;
}
