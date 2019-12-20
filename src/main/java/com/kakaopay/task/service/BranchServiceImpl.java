package com.kakaopay.task.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kakaopay.task.exception.ApiException;
import com.kakaopay.task.exception.NotFoundException;
import com.kakaopay.task.exception.dto.ErrorCode;
import com.kakaopay.task.repository.BranchRepository;
import com.kakaopay.task.repository.ClientRepository;
import com.kakaopay.task.repository.dto.BranchDto;
import com.kakaopay.task.repository.dto.CommonResult;

@Service
public class BranchServiceImpl implements BranchService{
	
	@Autowired
	ClientRepository clientRepository;
	
	@Autowired
	BranchRepository branchRepository;

	@Override
	public List<BranchDto.YearBranchRank> findYearBranchRank(String year){
		List<BranchDto.YearBranchRank> branchDtoList = new ArrayList<>();
		int dataCnt = 0;
		for(String y :year.split("\\,", -1)) {
			if(!"".contentEquals(y)) {
				List<BranchDto.YearBranchRank.DataList> dataList = branchRepository.findYearBranchRank(y);
				dataCnt += dataList.size();
				branchDtoList.add(
					BranchDto.YearBranchRank.builder()
					.year(y)
					.datalist(dataList)
					.build()
				);
			}
		}
		if(dataCnt == 0) throw new NotFoundException("DATA Not Found");
		return branchDtoList ;
	}
	
	@Override
	public List<BranchDto.BranchInfo> findBranchInfo(String name){
		List<BranchDto.BranchInfo> branchInfo  = new ArrayList<>();
		for(String n : name.split("\\,", -1)) {
			if(!"".equals(n)) branchInfo.addAll(branchRepository.findBranchInfo(n));
		}
		if(branchInfo.size() == 0) throw new NotFoundException("BR_NAME IS Not Found");
		return branchInfo;
	}
	
	@Override
	@Transactional(rollbackFor={ApiException.class})
	public CommonResult moveBranch(String fromCode, String toCode){
		branchRepository.deleteByBrCode(fromCode);
		int result = clientRepository.updateClientBranch(toCode, fromCode);
		if(result == 0  ) 		return CommonResult.success("이관할 정보가 존재하지 않습니다.");
		else if(result > 0 	) 	return CommonResult.success("정상적으로 이관되었습니다.");
		else		 			throw new ApiException("지점정보 이관 중 오류가 발생하였습니다.", ErrorCode.INTERNAL_SERVER_ERROR);
	}
}
