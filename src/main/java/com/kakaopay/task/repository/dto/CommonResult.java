package com.kakaopay.task.repository.dto;

import lombok.*;

/**
 * Common View Layout
 */

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CommonResult {
	private String result	= "success";
	private String message	= "";
	
	/**
	 * 성공
	 * @param message
	 * @return
	 */
	public static CommonResult success(String message) {
		return new CommonResult("success", message);
	}
	
	/**
	 * 실패
	 * @param message
	 * @return
	 */
	public static CommonResult error(String message) {
		return new CommonResult("success", message);
	}
}
