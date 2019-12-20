package com.kakaopay.task;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.kakaopay.task.common.TestDescription;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.*;

/**
 * 연도별 합계금액이 가장 많은 고객 추출 API에 대한 Test Case작성
 * @author Owner
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ApiClientTopControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	/**
	 * 허용되지 않은 Method 호출 (Get으로 호출)
	 * METHOD_NOT_ALLOWED(405)발생
	 */
	@Test
	@TestDescription("허용되지 않은 Method 호출")
	public void 허용되지_않은_Method_호출() throws Exception {
		//given
		
		//when
		final ResultActions actions =
			mockMvc.perform(get("/api/client/top")
				   .contentType(MediaType.APPLICATION_JSON)
				   .accept(MediaTypes.HAL_JSON_VALUE))
				.andDo(print());

		   //then
		actions
			.andExpect(status().isMethodNotAllowed())
			.andExpect(jsonPath("$.status").value(HttpStatus.METHOD_NOT_ALLOWED.value()));
		
	}
	
	/**
	 * 필수 파라미터 정보가 없는 상황
	 * 필수 입력값으로 INTERNAL_SERVER_ERROR(500) 발생
	 */
	@Test
	@TestDescription("필수 파라미터 정보가 없는경우")
	public void 필수_파라미터_정보가_없는경우() throws Exception {
		//given

		//when
		final ResultActions actions =
			mockMvc.perform(post("/api/client/top")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaTypes.HAL_JSON_VALUE))
				.andDo(print());

		//then
		actions
				.andExpect(status().isInternalServerError())
				.andExpect(jsonPath("$.status").value(HttpStatus.INTERNAL_SERVER_ERROR.value()));
	}

	/**
	 * 연도 필수 입력값이 비워져있는상황
	 * 필수값비워져 있으므로 BAD_REQUEST(400) 발생
	 */
	@Test
	@TestDescription("필수 입력값이 비어있는경우")
	public void 필수_입력값이_비어있는경우() throws Exception {
		//given

		//when
		final ResultActions actions =
			mockMvc.perform(post("/api/client/top").param("year", "")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaTypes.HAL_JSON_VALUE))
				.andDo(print());

		//then
		actions
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()));
	}
	
	/**
	 * 데이터가 존재하지 않는경우 
	 * Not Found (404) 발생
	 */
	@Test
	@TestDescription("결과데이터가 존재하지 않는경우")
	public void 결과데이터가_존재하지_않는경우() throws Exception {
		//given
		//when
		final ResultActions actions =
			mockMvc.perform(post("/api/client/top").param("year", "2020")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaTypes.HAL_JSON_VALUE))
				.andDo(print());

		//then
		actions
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()));
	}
	
	/**
	 * 정상적 입력값 호출
	 * 기대값 [{ "year": "2018", "name": "테드", "acctNo": "11111114",  "sumAmt": "28992000" }]
	 */
	@Test
	@TestDescription("정상적으로 호출시 기대값")
	public void 정상적으로_호출시_기대값() throws Exception {
		//given
		//when
		final ResultActions actions =
			mockMvc.perform(post("/api/client/top").param("year", "2018")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaTypes.HAL_JSON_VALUE))
				.andDo(print());

		//then
		actions
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].year"		).value("2018"		))
				.andExpect(jsonPath("$[0].name"		).value("테드"		))
				.andExpect(jsonPath("$[0].acctNo"	).value("11111114"	))
				.andExpect(jsonPath("$[0].sumAmt"	).value("28992000"	));
	}
	
	/**
	 * 정상적 입력값 호출 (입력항목이 여러개)
	 * 기대값 [
	 * 	{ "year": "2018", "name": "테드", "acctNo": "11111114",  "sumAmt": "28992000" },
	 *  { "year": "2019", "name": "에이스", "acctNo": "11111112", "sumAmt": "40998400" }
	 * ]
	 */
	@Test
	@TestDescription("입력항목이 여러개인 정상적으로 호출시 기대값")
	public void 입력항목이_여러개인_정상적으로_호출시_기대값() throws Exception {
		//given
		//when
		final ResultActions actions =
			mockMvc.perform(post("/api/client/top").param("year", "2018").param("year", "2019")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaTypes.HAL_JSON_VALUE))
				.andDo(print());

		//then
		actions
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].year"		).value("2018"		))
				.andExpect(jsonPath("$[0].name"		).value("테드"		))
				.andExpect(jsonPath("$[0].acctNo"	).value("11111114"	))
				.andExpect(jsonPath("$[0].sumAmt"	).value("28992000"	))
		
				.andExpect(jsonPath("$[1].year"		).value("2019"		))
				.andExpect(jsonPath("$[1].name"		).value("에이스"		))
				.andExpect(jsonPath("$[1].acctNo"	).value("11111112"	))
				.andExpect(jsonPath("$[1].sumAmt"	).value("40998400"	));
	}
}