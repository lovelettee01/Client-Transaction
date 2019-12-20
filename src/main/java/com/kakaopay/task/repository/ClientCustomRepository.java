package com.kakaopay.task.repository;

import java.util.List;
import com.kakaopay.task.repository.dto.ClientDto;

public interface ClientCustomRepository<T> {

	/*연도별 합계금액이 가장 많은 고객*/
	List<ClientDto.YearTopClient> findYearTopClient( String year );
	
	/*연도별 거래가 없는 고객(취소고객포함)*/
	List<ClientDto.YearNonClient> findYearNonClient( String year );
	
	/*연도별 거래가 없는 고객(취소고객미포함)*/
	List<ClientDto.YearNonClient> findYearNon_CancelClient( String year );
}
