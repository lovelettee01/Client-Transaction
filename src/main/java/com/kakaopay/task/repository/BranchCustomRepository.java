package com.kakaopay.task.repository;

import java.util.List;
import com.kakaopay.task.repository.dto.BranchDto;

public interface BranchCustomRepository<T> {

	/* 연도별 관리점 합계금액 */
	List<BranchDto.YearBranchRank.DataList> findYearBranchRank( String year );
	
	/* 관리점 정보*/
	List<BranchDto.BranchInfo> findBranchInfo( String name );
}
