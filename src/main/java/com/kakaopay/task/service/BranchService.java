package com.kakaopay.task.service;

import java.util.List;
import com.kakaopay.task.repository.dto.BranchDto;
import com.kakaopay.task.repository.dto.CommonResult;

public interface BranchService {

	/* 연도별 관리점 합계금액 */
	List<BranchDto.YearBranchRank> findYearBranchRank(String year);
	
	/* 관리점 정보 */
	List<BranchDto.BranchInfo> findBranchInfo(String name);
	
	/* 관리점 이관 */
	CommonResult moveBranch(String fromCode, String toCode);
}
