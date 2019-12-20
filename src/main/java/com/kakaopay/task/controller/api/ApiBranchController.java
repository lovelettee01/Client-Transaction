package com.kakaopay.task.controller.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kakaopay.task.exception.InvalidValueException;
import com.kakaopay.task.repository.dto.BranchDto;
import com.kakaopay.task.repository.dto.CommonResult;
import com.kakaopay.task.service.BranchService;

@RestController
@RequestMapping(value="/api/branch", produces = MediaTypes.HAL_JSON_VALUE)
public class ApiBranchController {
	
	@Autowired
	private BranchService branchService;
	
	/**
	 * 연도별 관리점 합계금액 순위 추출 API
	 * @param year 연도
	 */
	@PostMapping("/rank")
	public ResponseEntity <List<BranchDto.YearBranchRank>> findYearBranchRank (@Valid @RequestParam("year") String year){
		if(year == null || "".equalsIgnoreCase(year)) throw new InvalidValueException("YEAR is Null");
		List<BranchDto.YearBranchRank> branchDto = branchService.findYearBranchRank(year);
		return ResponseEntity.ok(branchDto);
	}
	
	/**
	 * 관리점 정보 추출 API
	 * @param name 관리점명
	 */
	@PostMapping("/info")
	public ResponseEntity <List<BranchDto.BranchInfo>> findBranchInfo (@Valid @RequestParam("name") String name){
		if(name == null || "".equalsIgnoreCase(name)) throw new InvalidValueException("BR NAME is Null");
		List<BranchDto.BranchInfo> branchDto = branchService.findBranchInfo(name);
		return ResponseEntity.ok(branchDto);
	}
	
	/**
	 * 관리점 이관 API
	 */
	@PutMapping("/move")
	public ResponseEntity <CommonResult> moveBranch (@Valid @RequestParam("fromCode") String fromCode, @Valid @RequestParam("toCode") String toCode){
		if(fromCode == null || "".equalsIgnoreCase(fromCode)) throw new InvalidValueException("From Br CODE is Null");
		if(toCode == null || "".equalsIgnoreCase(toCode)) throw new InvalidValueException("To Br CODE is Null");
		return ResponseEntity.ok(branchService.moveBranch(fromCode, toCode));
	}
}
