package com.kakaopay.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kakaopay.task.repository.entity.Transaction;

/**
 * 거래내역 정보 Repository
 */
public interface TransRepository extends JpaRepository<Transaction, Long>{

}
