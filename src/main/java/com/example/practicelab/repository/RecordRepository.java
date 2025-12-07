package com.example.practicelab.repository;

import com.example.practicelab.model.InvestmentRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordRepository extends JpaRepository<InvestmentRecord,Long> {
    boolean existsByCustomerNumber(int customerNumber);
}
