package com.fbn.case_study.repository;

import com.fbn.case_study.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, String> {
    boolean existsByAccountNumber(String accountNumber);
    Optional<Customer> findByAccountNumber(String accountNumber);
}
