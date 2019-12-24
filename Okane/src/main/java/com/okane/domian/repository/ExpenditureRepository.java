package com.okane.domian.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.okane.domain.entity.Expenditure;

@Repository
public interface ExpenditureRepository extends JpaRepository<Expenditure, Integer> {
	 Page<Expenditure> findAllExpenditureByUserIdAndType(int userId,String type, Pageable pageable) ;
}
