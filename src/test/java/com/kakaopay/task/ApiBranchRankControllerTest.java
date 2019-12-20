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
public class ApiBranchRankControllerTest {

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
			mockMvc.perform(get("/api/branch/rank")
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
			mockMvc.perform(post("/api/branch/rank")
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
			mockMvc.perform(post("/api/branch/rank").param("year", "")
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
			mockMvc.perform(post("/api/branch/rank").param("year", "2020")
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
 	* 기대값 [
	*     {
	*         "year": "2018",
	*         "datalist": [
	*             { "brCode": "B", "brName": "분당점", "sumAmt": "38484000" },
	*             { "brCode": "A", "brName": "판교점", "sumAmt": "20505700" },
	*             { "brCode": "C", "brName": "강남점", "sumAmt": "20232867" },
	*             { "brCode": "D", "brName": "잠실점", "sumAmt": "14000000" }
	*         ]
	*     }
	* ]
	*/
	@Test
	@TestDescription("정상적으로 호출시 기대값")
	public void 정상적으로_호출시_기대값() throws Exception {
		//given
		//when
		final ResultActions actions =
			mockMvc.perform(post("/api/branch/rank").param("year", "2018")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaTypes.HAL_JSON_VALUE))
				.andDo(print());

		//then
		actions
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].datalist[0].brCode").value("B"		))
				.andExpect(jsonPath("$[0].datalist[0].brName").value("분당점"		))
				.andExpect(jsonPath("$[0].datalist[0].sumAmt").value("38484000"	))
				
				.andExpect(jsonPath("$[0].datalist[1].brCode").value("A"		))
				.andExpect(jsonPath("$[0].datalist[1].brName").value("판교점"		))
				.andExpect(jsonPath("$[0].datalist[1].sumAmt").value("20505700"	))		

				.andExpect(jsonPath("$[0].datalist[2].brCode").value("C"		))
				.andExpect(jsonPath("$[0].datalist[2].brName").value("강남점"		))
				.andExpect(jsonPath("$[0].datalist[2].sumAmt").value("20232867"	))		
				
				.andExpect(jsonPath("$[0].datalist[3].brCode").value("D"		))
				.andExpect(jsonPath("$[0].datalist[3].brName").value("잠실점"		))
				.andExpect(jsonPath("$[0].datalist[3].sumAmt").value("14000000"	));	
		
	}
	
	/**
 	* 정상적 입력값 호출  (입력항목이 여러개)
 	* 기대값 [
	*     {
	*         "year": "2018",
	*         "datalist": [
	*             { "brCode": "B", "brName": "분당점", "sumAmt": "38484000" },
	*             { "brCode": "A", "brName": "판교점", "sumAmt": "20505700" },
	*             { "brCode": "C", "brName": "강남점", "sumAmt": "20232867" },
	*             { "brCode": "D", "brName": "잠실점", "sumAmt": "14000000" }
	*         ]
	*     },
	*     {
	*         "year": "2019",
	*         "datalist": [
	*             { "brCode": "A", "brName": "판교점", "sumAmt": "66795100" },
	*             { "brCode": "B", "brName": "분당점", "sumAmt": "45396700" },
	*             { "brCode": "C", "brName": "강남점", "sumAmt": "19500000" },
	*             { "brCode": "D", "brName": "잠실점", "sumAmt": "6000000"  }
	*         ]
	*     }
	* ]
	*/
	@Test
	@TestDescription("입력항목이 여러개인 정상적으로 호출시 기대값")
	public void 입력항목이_여러개인_정상적으로_호출시_기대값() throws Exception {
		//given
		//when
		final ResultActions actions =
			mockMvc.perform(post("/api/branch/rank").param("year", "2018").param("year", "2019")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaTypes.HAL_JSON_VALUE))
				.andDo(print());

		//then
		actions
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].datalist[0].brCode").value("B"		))
				.andExpect(jsonPath("$[0].datalist[0].brName").value("분당점"		))
				.andExpect(jsonPath("$[0].datalist[0].sumAmt").value("38484000"	))
				
				.andExpect(jsonPath("$[0].datalist[1].brCode").value("A"		))
				.andExpect(jsonPath("$[0].datalist[1].brName").value("판교점"		))
				.andExpect(jsonPath("$[0].datalist[1].sumAmt").value("20505700"	))		

				.andExpect(jsonPath("$[0].datalist[2].brCode").value("C"		))
				.andExpect(jsonPath("$[0].datalist[2].brName").value("강남점"		))
				.andExpect(jsonPath("$[0].datalist[2].sumAmt").value("20232867"	))		
				
				.andExpect(jsonPath("$[0].datalist[3].brCode").value("D"		))
				.andExpect(jsonPath("$[0].datalist[3].brName").value("잠실점"		))
				.andExpect(jsonPath("$[0].datalist[3].sumAmt").value("14000000"	))
				
				.andExpect(jsonPath("$[1].datalist[0].brCode").value("A"		))
				.andExpect(jsonPath("$[1].datalist[0].brName").value("판교점"		))
				.andExpect(jsonPath("$[1].datalist[0].sumAmt").value("66795100"	))
				
				.andExpect(jsonPath("$[1].datalist[1].brCode").value("B"		))
				.andExpect(jsonPath("$[1].datalist[1].brName").value("분당점"		))
				.andExpect(jsonPath("$[1].datalist[1].sumAmt").value("45396700"	))		

				.andExpect(jsonPath("$[1].datalist[2].brCode").value("C"		))
				.andExpect(jsonPath("$[1].datalist[2].brName").value("강남점"		))
				.andExpect(jsonPath("$[1].datalist[2].sumAmt").value("19500000"	))		
				
				.andExpect(jsonPath("$[1].datalist[3].brCode").value("D"		))
				.andExpect(jsonPath("$[1].datalist[3].brName").value("잠실점"		))
				.andExpect(jsonPath("$[1].datalist[3].sumAmt").value("6000000"	));
				
	}
}