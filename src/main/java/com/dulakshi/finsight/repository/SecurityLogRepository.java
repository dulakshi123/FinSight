package com.dulakshi.finsight.repository;

import com.dulakshi.finsight.entity.SecurityLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SecurityLogRepository extends JpaRepository<SecurityLog, Integer> {
    List<SecurityLog> findByUserId(Integer userId);
}