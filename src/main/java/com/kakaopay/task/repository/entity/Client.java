package com.kakaopay.task.repository.entity;

import java.io.Serializable;
import java.util.*;
import javax.persistence.*;
import com.kakaopay.task.repository.dto.ClientDto.*;

import lombok.*;

/**
 * 고객정보 Entity
 * @author Kyung Hoon
 */

@SqlResultSetMapping(name = "YearTopClientResult", classes = {
    @ConstructorResult(
		targetClass = YearTopClient.class,
	    columns = {
	        @ColumnResult(name = "year"		, type= String.class),
	        @ColumnResult(name = "name"		, type= String.class),
	        @ColumnResult(name = "acctNo"	, type= String.class),
	        @ColumnResult(name = "sumAmt"	, type= String.class)
	})
})

@SqlResultSetMapping(name = "YearNonClientResult", classes = {
    @ConstructorResult(
		targetClass = YearNonClient.class,
	    columns = {
	        @ColumnResult(name = "year"		, type= String.class),
	        @ColumnResult(name = "name"		, type= String.class),
	        @ColumnResult(name = "acctNo"	, type= String.class),
	})
})
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@Table
public class Client implements Serializable{
	
	private static final long serialVersionUID  = 810457109758330244L;
	
	@Id
	@Column(length = 8, unique = true, nullable = false)
	private String acctNo;		//계좌번호
	
	@Column(length = 50, nullable = false)
	private String name;		//계좌명
	
	@Column(length = 1, nullable = false)
	private String brCode;		//지점코드
	
	/*
	@ManyToOne
	@JoinColumn(name="brCode")
	private Branch branch;		//관리점코드(Branch_brCode - foreignKey)
	
	@OneToMany(mappedBy = "client")
	private List<Transaction> trans = new ArrayList<>();
	
	public void addTransaction(Transaction trans){
        this.trans.add(trans);
        trans.setClient(this);
    }
    */
}
