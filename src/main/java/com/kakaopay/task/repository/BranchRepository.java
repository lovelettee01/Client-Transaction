package com.kakaopay.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kakaopay.task.repository.dto.BranchDto;
import com.kakaopay.task.repository.entity.Branch;

/**
 * 관리점 정보 Repository
 */
public interface BranchRepository extends JpaRepository<Branch, String>, BranchCustomRepository<BranchDto>{
	void deleteByBrCode(String code);
}
