package com.kakaopay.task.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Main Controller
 */
@Controller
public class MainController {
	
	/**
	 * Index 페이지
	 * @return
	 */
	@GetMapping("/")
	public String main(){
		return "index";
	}
}
