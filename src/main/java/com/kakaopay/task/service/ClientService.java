package com.kakaopay.task.service;

import java.util.List;

import com.kakaopay.task.repository.dto.ClientDto;
import com.kakaopay.task.repository.entity.Client;
public interface ClientService {
	
	List<Client> findAllClient();
	
	/*연도별 합계금액이 가장 많은 고객*/
	List<ClientDto.YearTopClient> findYearTopClient(String year);
	
	/*연도별 거래가 없는 고객*/
	List<ClientDto.YearNonClient> findYearNonClient(String year, String embedCancel);
}
