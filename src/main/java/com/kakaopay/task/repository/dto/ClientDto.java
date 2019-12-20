package com.kakaopay.task.repository.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.*;

/**
 * 고객 View Layout
 */
public class ClientDto {

	/**
	 * 연도별 합계금액이 가장많은고객 정보
	 */
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	@Data
	public static class YearTopClient {
		@NotNull @NotEmpty
		private String year;
		@NotEmpty
		private String name;
		@NotEmpty
		private String acctNo;
		@NotEmpty
		private String sumAmt;
	}
	
	/**
	 * 연도별 거래가 거래가 없는 고객정보
	 */
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	@Data
	public static class YearNonClient {
		@NotNull @NotEmpty
		private String year;
		@NotEmpty
		private String name;
		@NotEmpty
		private String acctNo;
	}
}
