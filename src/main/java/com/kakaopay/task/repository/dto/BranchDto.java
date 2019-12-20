package com.kakaopay.task.repository.dto;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.*;

/**
 * 관리점 View Laoyout
 */
public class BranchDto {

	/**
	 * 연도별 관리점 합계금액 순위
	 */
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	@Data
	public static class YearBranchRank {
		@NotNull @NotEmpty
		private String year;
		private List<DataList> datalist;
		
		@Builder
		@NoArgsConstructor
		@AllArgsConstructor
		@Data
		public static class DataList{
			@NotEmpty
			private String brCode;
			@NotEmpty
			private String brName;
			@NotEmpty
			private String sumAmt;
		}
	}
	
	/**
	 * 관리점 정보
	 */
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	@Data
	public static class BranchInfo {
		@NotNull @NotEmpty
		private String brCode;
		@NotEmpty
		private String brName;
		@NotEmpty
		private String sumAmt;
	}	
}
