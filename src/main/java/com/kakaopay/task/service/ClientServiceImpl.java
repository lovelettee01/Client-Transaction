package com.kakaopay.task.service;

import java.util.*;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kakaopay.task.exception.ApiException;
import com.kakaopay.task.exception.InvalidValueException;
import com.kakaopay.task.exception.NotFoundException;
import com.kakaopay.task.exception.dto.ErrorCode;
import com.kakaopay.task.repository.ClientRepository;
import com.kakaopay.task.repository.dto.ClientDto;
import com.kakaopay.task.repository.entity.Client;

@Service
public class ClientServiceImpl implements ClientService{
	
	@Autowired
	ClientRepository clientRepository;
	
	@Override
	public List<Client> findAllClient(){
		return clientRepository.findAll();
	}
	
	@Override
	public List<ClientDto.YearTopClient> findYearTopClient(String year){
		List<ClientDto.YearTopClient> clientDtoList = new ArrayList<>();
		for(String y :year.split("\\,", -1)) {
			if(!"".equals(y)) clientDtoList.addAll(clientRepository.findYearTopClient(y));
		}
		if(clientDtoList.size() == 0) throw new NotFoundException("DATA Not Found");
		return clientDtoList;
	}
	
	@Override
	public List<ClientDto.YearNonClient> findYearNonClient(String year, String embedCancel){
		List<ClientDto.YearNonClient> clientDtoList = new ArrayList<>();
		for(String y :year.split("\\,", -1)) {
			if(!"".equals(y)) {
				if("Y".equalsIgnoreCase(embedCancel)) 	clientDtoList.addAll(clientRepository.findYearNonClient(y));
				else									clientDtoList.addAll(clientRepository.findYearNon_CancelClient(y));			
			}
		}
		if(clientDtoList.size() == 0) throw new NotFoundException("DATA Not Found");
		return clientDtoList;
	}
}
