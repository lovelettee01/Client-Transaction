package com.kakaopay.task.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.*;

import com.kakaopay.task.repository.dto.BranchDto;

@Repository
@Transactional
public class BranchCustomRepositoryImpl implements BranchCustomRepository {

    @Autowired
    EntityManager entityManager;

    @Override
    public List<BranchDto.YearBranchRank.DataList> findYearBranchRank( String year ) {
    	String sql = 
    			"select b.br_code brCode, b.br_name brName, sumAmt from ( " +
				"	select br_code,sum(sumAmt) sumAmt from( " +
				"		select c.br_code br_code, c.name name, substr(date, 0, 4) year, sum(amt)-sum(fee) sumAmt from client c inner join transaction t  where c.acct_no = t.acct_no and t.cancel_status <> 'Y' group by c.acct_no , year " +
				"	) where year=?1 group by br_code " + 
				") a , branch b where a.br_code = b.br_code order by sumAmt desc";
    	Query query = entityManager.createNativeQuery(sql, "YearBranchRankResult");
    	query.setParameter(1, year);
    	
    	return query.getResultList();
    }
    
    @Override
    public List<BranchDto.BranchInfo> findBranchInfo( String name ) {
    	String sql = 
    			"select b.br_code brCode, b.br_name brName, sumAmt from ( " +
				"	select br_code, sum(sumAmt) sumAmt from( " +
				"		select c.br_code , substr(date, 0, 4) year, sum(amt)-sum(fee) sumAmt from client c inner join transaction t  where c.acct_no = t.acct_no and t.cancel_status <> 'Y' group by c.acct_no , year " +
				"	) group by br_code " +
				") a , branch b where a.br_code = b.br_code and b.br_name=?1";
    	Query query = entityManager.createNativeQuery(sql, "BranchInfoResult");
    	query.setParameter(1, name);
    	
    	return query.getResultList();
    }
}
