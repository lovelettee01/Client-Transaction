package com.kakaopay.task.repository.entity;

import java.io.Serializable;
import javax.persistence.*;

import com.kakaopay.task.constant.CancelStatus;

import lombok.*;

/**
 * 거래내역 Entity
 * @author Kyung Hoon
 */

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@Table
public class Transaction implements Serializable{
	
	private static final long serialVersionUID  = 810457109758330244L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long seq;				//일련번호
	
	@Column(length = 8, nullable = false)
	private String date;			//거래일자
	
	@Column(length = 3, nullable = false)
	private Integer idx;			//거래일련번호
	
	@Column(length = 15, nullable = false)
	private Integer amt;			//거래금액
	
	@Column(length = 10)
	private Integer fee;			//거래수수료
	
	@Column(length = 1)
	@Enumerated(EnumType.STRING)	//취소여부(Default : N)
	private CancelStatus cancelStatus = CancelStatus.N;
	
	@Column(length = 8 , nullable = false)
	private String acctNo;			//계좌번호
	
	//@ManyToOne
	//@JoinColumn(name="acctNo")
	//private Client client;			//계좌번호(Client_acctNo - foreignKey)
}
