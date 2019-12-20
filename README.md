## 고객 거래내역 조회 서비스

### 1.요청사항
>  - 고객을 타겟팅 하기 위한 지점 마케팅 API를 개발
> - 데이타에는 거래내역, 고객, 지점정보가 있습니다. 
> - 각 데이타를 활용하여 아래 기능명세에 대한 API를 개발
<hr />

### 2.개발환경
>  - JAVA8
>  - Spring-boot (2.2.2.RELEASE)
> - JPA & Hibernate
>  - Maven
>  - jUnit
<hr />

### 3. API 기능명세
> - 모든 입/출력은 JSON 형태로 주고 받음
> - 각 API에 대한 HTTP Method (GET|POST|PUT|DELETE) 는 자유롭게사용
#### 3.1 연도별 합계금액이 가장 많은 고객을 추출하는 API
```css
Output :
 	[{	
 		“year”   	: "연도",
 		“name” 		: "계좌명",
 		“acctNo”	: "계좌번호",  
 		“sumAmt”	: "합계금액"
 	 },
 	 ..
 	 {	
 		“year”   	: "연도",
 		“name” 		: "계좌명",
 		“acctNo”	: "계좌번호",  
 		“sumAmt”	: "합계금액"
 	}]
```
  	
#### 3.2연도별 거래가 없는고객을 추출하는 API
```css
Output :
	[{	
		“year”   	: "연도",
		“name” 		: "계좌명",
		“acctNo”	: "계좌번호"
	 },
	 ......
	 {	
		“year”   	:"연도",
		“name” 		:"계좌명",
		“acctNo”	:"계좌번호"
	}]
```
#### 3.3 연도별 관리점 거래금액합계가 큰순서로 출력하는 API
```css
Output :
   	[{	
   		“year”   	:"연도",
   		"dataList"	:[{
	   		“name” 		: "계좌명",
	   		“acctNo”	: "계좌번호",  
	   		“sumAmt”	: "합계금액"
		   	},
		   	....
	   	]
   	 },
   	 ..
   	 {	
   		“year”   	:"연도",
   		"dataList"	:[{
	   		“name” 		: "계좌명",
	   		“acctNo”	: "계좌번호",  
	   		“sumAmt”	: "합계금액"
		   	},
		   	....
	   	]
   	}]
```
#### 3.3 관리점 정보  및 통폐합 및 이관 API
##### 3.4.1 관리점 정보
```css
Input:
	{
		“brName”	: "관리점명"
	}
```
```css
Output :
	{	
		“brCode”	: "관리점코드",
	   	“brName”   	: "관리점명",
	   	“sumAmt”	: "관리점명"
	}
```
##### 3.4.2 관리점 통폐합 및 이관
```css
Output :
	{	
	  	“result”   	: "성공여부",
	   	“message”	: "메시지"
 	}
```
<hr />

### 4. API Document
>  URL : https://lovelettee01.iptime.org:8088/
#### 4.1 연도별 합계금액이 가장 많은 고객을 추출하는 API
> URI 			:  /api/client/top   
>  Method 	: POST
#### `Parameter`
| Name  | Type   | Desc		| 필수여부 |	
| :---- | :----  | :------- |:------- |
| `year`| String | 조회연도	| Y 	  |

#### 4.2 연도별 거래가 없는고객을 추출하는 API
> URI 			:  /api/client/non   
>  Method 	: POST
#### `Parameter`
| Name  		| Type   | Desc			| 필수여부 		|	
| :-------------| :----  | :----------- |:------------- |
| `year`		| String | 조회연도		| Y 	  		|
| `embedCancel`	| String | 취소고객포함여부| N(Default:N) 	|  

#### 4.3 연도별 관리점 합계금액 순위 추출 API
> URI 			:  /api/branch/rank   
>  Method 	: POST
#### `Parameter`
| Name  | Type   | Desc		| 필수여부 |	
| :---- | :----  | :------- |:------- |
| `year`| String | 조회연도	| Y 	  |

#### 4.4 관리점 정보 추출 API
> URI 			:  /api/branch/info  
>  Method 	: POST
#### `Parameter`
| Name  | Type   | Desc		| 필수여부 |	
| :---- | :----  | :------- |:------- |
| `name`| String | 관리점명 	| Y 	  |

#### 4.5 관리점 이관  API
> URI 			:  /api/branch/move   
>  Method 	: PUT
#### `Parameter`
| Name  	| Type   | Desc				| 필수여부  	|	
| :-------- | :----  | :--------------- |:--------- |
| `formCode`| String | 이관대상지점코드	| Y 	  	|
| `toCode`	| String | 이관목표지점코드	| Y 	  	|
<hr />

### 5. 빌드 및 실행
```sh
maven package & java -jar Kakaopay-Task-0.0.1-SNAPSHOT.jar
```
<hr />

### 6. 개발 요약
> - Spring-boot를 이용한 Restful Api 개발
> - Maven을 이용한 Project library dependency 관리
> - Embedded Tomcat을 이용한 서버 구성
> - H2 Memory DB를 이용한 데이터 관리
> - JPA & Hibernate를 이용한 Databae Access
<hr />

### 마치며
> Spring-boot를 이용한 Application 개발의 심플함에 푹빠져 API를 개발하는것도 잠시..
> 버전업된 수많은 Annotation 과 JPA를 이용한 데이터 접근, Obeject Mapping, 
> JUnit를 이용한 Test Case 까지 개발자의 삶은 항상 도전과 열정을 필요로 한다
<hr />

### Reference
>   Spring-boot, JPA, Restful API에 대한 설명이 잘되어있음
>> [https://engkimbs.tistory.com/category/Spring/Spring%20Boot](https://engkimbs.tistory.com/category/Spring/Spring%20Boot)  
>
> 스프링 데이터 JPA 레퍼런스 번역
>>[https://arahansa.github.io/docs_spring/jpa.html](https://arahansa.github.io/docs_spring/jpa.html)
>
> Spring Data JPA - Reference Documentation
>> [https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation)
>
> JPQL / Criteria API
> >[https://www.objectdb.com/java/jpa/query/jpql/from](https://www.objectdb.com/java/jpa/query/jpql/from)
>
>Unit Testing with JUnit - Tutorial
>> [https://www.vogella.com/tutorials/JUnit/article.html#junit5](https://www.vogella.com/tutorials/JUnit/article.html#junit5)
