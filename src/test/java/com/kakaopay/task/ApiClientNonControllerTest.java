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

import static org.hamcrest.Matchers.*;
import com.kakaopay.task.common.TestDescription;
import com.kakaopay.task.repository.ClientRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * 연도별 거래가 없는 고객을 추출하는 API에 대한 Test Case작성
 * @author Owner
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ApiClientNonControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
	ClientRepository clientRepository;

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
			mockMvc.perform(get("/api/client/non")
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
			mockMvc.perform(post("/api/client/non")
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
			mockMvc.perform(post("/api/client/non").param("year", "")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaTypes.HAL_JSON_VALUE))
				.andDo(print());

		//then
		actions
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()));
	}
	
	/**
	 * 미래년도 입력시 모든 고객 리스트 반환(아직 거래될수가 없는 조건)
	 * 카운트수가 모든 고객수와 일치
	 */
	@Test
	@TestDescription("미래년도 입력시 모든 고객 리스트 반환")
	public void 미래년도_입력시_모든_고객_리스트_반환() throws Exception {
		//given

		//Client 카운트
		long count = clientRepository.count();
		
		//when
		final ResultActions actions =
			mockMvc.perform(post("/api/client/non").param("year", "2020")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaTypes.HAL_JSON_VALUE))
				.andDo(print());

		//then
		actions
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.*", hasSize((int)count)));
	}
	
	/**
	 * 정상적 입력값 호출
	 * 기대값 [{ "year": "2018", "name": "사라", "acctNo": "11111115" }]
	 */
	@Test
	@TestDescription("정상적으로 호출시 기대값")
	public void 정상적으로_호출시_기대값() throws Exception {
		//given
		//when
		final ResultActions actions =
			mockMvc.perform(post("/api/client/non").param("year", "2018")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaTypes.HAL_JSON_VALUE))
				.andDo(print());

		//then
		actions
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].year"		).value("2018"		))
				.andExpect(jsonPath("$[0].name"		).value("사라"		))
				.andExpect(jsonPath("$[0].acctNo"	).value("11111115"	));
	}
	
	/**
	 * 정상적 입력값 호출 - 취소포함
	 * 기대값 [
	 * 	{ "year": "2018", "name": "사라"	, "acctNo": "11111115" 	},
	 *  { "year": "2018", "name": "제임스", "acctNo": "11111118" 	}
	 * ]
	 */
	@Test
	@TestDescription("정상적으로 호출시 기대값 취소포함")
	public void 정상적으로_호출시_기대값_취소포함() throws Exception {
		//given
		//when
		final ResultActions actions =
			mockMvc.perform(post("/api/client/non").param("year", "2018").param("embedCancel", "Y")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaTypes.HAL_JSON_VALUE))
				.andDo(print());

		//then
		actions
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].year"		).value("2018"		))
				.andExpect(jsonPath("$[0].name"		).value("사라"		))
				.andExpect(jsonPath("$[0].acctNo"	).value("11111115"	))
				
				.andExpect(jsonPath("$[1].year"		).value("2018"		))
				.andExpect(jsonPath("$[1].name"		).value("제임스"		))
				.andExpect(jsonPath("$[1].acctNo"	).value("11111118"	));
	}
	
	/**
	 * 정상적 입력값 호출 (입력항목이 여러개)
	 * 기대값 [
	 * 	{ "year": "2018", "name": "사라", "acctNo": "11111115" },
	 *  { "year": "2019", "name": "테드", "acctNo": "11111114" }
	 * ]
	 */
	@Test
	@TestDescription("입력항목이 여러개인 정상적으로 호출시 기대값")
	public void 입력항목이_여러개인_정상적으로_호출시_기대값() throws Exception {
		//given
		//when
		final ResultActions actions =
			mockMvc.perform(post("/api/client/non").param("year", "2018").param("year", "2019")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaTypes.HAL_JSON_VALUE))
				.andDo(print());

		//then
		actions
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].year"		).value("2018"		))
				.andExpect(jsonPath("$[0].name"		).value("사라"		))
				.andExpect(jsonPath("$[0].acctNo"	).value("11111115"	))
		
				.andExpect(jsonPath("$[1].year"		).value("2019"		))
				.andExpect(jsonPath("$[1].name"		).value("테드"		))
				.andExpect(jsonPath("$[1].acctNo"	).value("11111114"	));
	}
}