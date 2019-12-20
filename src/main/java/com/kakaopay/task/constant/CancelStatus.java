package com.kakaopay.task.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 거래내역 취소여부 상수정의 
 */
@Getter
@AllArgsConstructor
public enum CancelStatus {
	 Y("Y", "취소"), N("N", "");
	
	private String code;
	private String name;
}
