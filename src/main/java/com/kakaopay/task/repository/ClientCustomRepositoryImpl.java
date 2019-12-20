package com.kakaopay.task.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.*;

import com.kakaopay.task.repository.dto.ClientDto;

@Repository
@Transactional
public class ClientCustomRepositoryImpl implements ClientCustomRepository {

    @Autowired
    EntityManager entityManager;

    @Override
    public List<ClientDto.YearTopClient> findYearTopClient( String year ) {
    	String sql = 
    			"select * from(" + 
				"  select c.acct_no acctNo, c.name name, substr(date, 0, 4) year, sum(amt)-sum(fee) sumAmt from client c inner join transaction t  where c.acct_no = t.acct_no and t.cancel_status <> 'Y' group by c.acct_no, year order by sumAmt desc" + 
				") Where year=?1";
    	Query query = entityManager.createNativeQuery(sql, "YearTopClientResult").setMaxResults(1);
    	query.setParameter(1, year);
    	
    	return query.getResultList();
    }
    
    @Override
    public List<ClientDto.YearNonClient> findYearNonClient( String year ) {
    	String sql = 
    			"select acct_no acctNo, name, ?1 year from client where acct_no not in  ( " +
				"	select acct_no from( " +
				"		select acct_no, substr(date, 0, 4) year from transaction  where  cancel_status <> 'Y' group by acct_no, year " +
				"	) where year =?2 " +
				")";
    	Query query = entityManager.createNativeQuery(sql, "YearNonClientResult");
    	query.setParameter(1, year);
    	query.setParameter(2, year);
    	
    	return query.getResultList();
    }
    
    @Override
    public List<ClientDto.YearNonClient> findYearNon_CancelClient( String year ) {
    	String sql = 
    			"select acct_no acctNo, name, ?1 year from client where acct_no not in  ( " +
				"	select acct_no from( " +
				"		select acct_no, substr(date, 0, 4) year from transaction group by acct_no, year " +
				"	) where year =?2 " +
				")";
    	Query query = entityManager.createNativeQuery(sql, "YearNonClientResult");
    	query.setParameter(1, year);
    	query.setParameter(2, year);
    	
    	return query.getResultList();
    }
}
