package com.kakaopay.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.kakaopay.task.repository.dto.ClientDto;
import com.kakaopay.task.repository.entity.Client;

/**
 * Client 정보 Repository
 */
public interface ClientRepository extends JpaRepository<Client, String>, ClientCustomRepository<ClientDto>{
	
	@Modifying(flushAutomatically = true)
	@Query(value="update Client set br_code = ?1 where br_code= ?2", nativeQuery = true)
	int updateClientBranch(String moveCode, String originCode);
}
