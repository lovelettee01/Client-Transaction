package com.kakaopay.task.repository.entity;

import java.io.Serializable;
import javax.persistence.*;

import com.kakaopay.task.repository.dto.BranchDto;

import lombok.*;

/**
 * 관리점 정보 Entity
 * @author Kyung Hoon
 */
@SqlResultSetMapping(name = "YearBranchRankResult", classes = {
    @ConstructorResult(
		targetClass = BranchDto.YearBranchRank.DataList.class,
	    columns = {
	        @ColumnResult(name = "brCode"	, type= String.class),
	        @ColumnResult(name = "brName"	, type= String.class),
	        @ColumnResult(name = "sumAmt"	, type= String.class)
	})
})

@SqlResultSetMapping(name = "BranchInfoResult", classes = {
    @ConstructorResult(
		targetClass = BranchDto.BranchInfo.class,
	    columns = {
	        @ColumnResult(name = "brCode"	, type= String.class),
	        @ColumnResult(name = "brName"	, type= String.class),
	        @ColumnResult(name = "sumAmt"	, type= String.class),
	})
})
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@Table
public class Branch implements Serializable{
	
	private static final long serialVersionUID  = 810457109758330244L;

	@Id
	@Column(length = 1, unique = true, nullable = false)
	private String brCode;		//지점코드
	
	@Column(length = 50, nullable = false)
	private String brName;		//지점명
	
	//@OneToMany(mappedBy = "branch")
	//private List<Client> clients = new ArrayList<>();
}
