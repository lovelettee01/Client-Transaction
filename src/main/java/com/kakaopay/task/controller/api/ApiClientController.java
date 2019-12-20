package com.kakaopay.task.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import com.kakaopay.task.exception.InvalidValueException;
import com.kakaopay.task.repository.dto.ClientDto;
import com.kakaopay.task.repository.entity.Client;
import com.kakaopay.task.service.ClientService;

import java.util.List;
import javax.validation.Valid;

@RestController
@RequestMapping(value="/api/client", produces = MediaTypes.HAL_JSON_VALUE)
public class ApiClientController {
	
	@Autowired
	private ClientService clientservice;
	
	/**
	 * 고객리스트
	 * @return
	 */
	@GetMapping("/all")
	public ResponseEntity <List<Client>> clientAll (){
		return ResponseEntity.ok(clientservice.findAllClient());
	}

	/**
	 * 연도별 합계금액이 가장 많은 고객 추출 API
	 * @param year 연도
	 */
	@PostMapping("/top")
	public ResponseEntity <List<ClientDto.YearTopClient>> YearTopClient (@Valid @NonNull @RequestParam("year") String year){
		if(year == null || "".equalsIgnoreCase(year)) throw new InvalidValueException("YEAR is Null");
		List<ClientDto.YearTopClient> clientDto = clientservice.findYearTopClient(year);
		return ResponseEntity.ok(clientDto);
	}
	
	/**
	 * 연도별 거래가 없는 고객을 추출하는 API
	 * @param year 연도
	 * @param embedCancel 취소고객포함여부 ('Y':취소고객포함, 'N':취소고객미포함)
	 */
	@PostMapping("/non")
	public ResponseEntity <List<ClientDto.YearNonClient>> YearNonClient (@Valid @RequestParam("year") String year, @Nullable @RequestParam("embedCancel") String embedCancel){
		if(year == null || "".equalsIgnoreCase(year)) throw new InvalidValueException("YEAR is Null");
		List<ClientDto.YearNonClient> clientDto = clientservice.findYearNonClient(year, embedCancel);
		return ResponseEntity.ok(clientDto);
	}
}
