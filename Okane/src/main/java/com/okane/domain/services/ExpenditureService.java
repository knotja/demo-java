package com.okane.domain.services;

import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.okane.domain.entity.Expenditure;
import com.okane.domain.entity.User;
import com.okane.domian.repository.ExpenditureRepository;
import com.okane.user.ExpenditureForm;

@Service
public class ExpenditureService extends UserService {

	@Autowired
	ExpenditureRepository expenditureRepository;

	public void createExpenditure(ExpenditureForm ExpenditureFrom, Principal principal)
			throws NoSuchAlgorithmException {
		Date nowDate = Calendar.getInstance().getTime();
		Expenditure expenditure = new Expenditure();
		expenditure.setType(ExpenditureFrom.getType());
		expenditure.setDetail(ExpenditureFrom.getDetail());
		expenditure.setValue(ExpenditureFrom.getValue());
		expenditure.setCreated(nowDate);
		expenditure.setUser(findOne(principal));
		expenditureRepository.save(expenditure);

		User user = findOne(principal);
		user.setReserve(user.getReserve() - ExpenditureFrom.getValue());
		userRepository.save(user);
	}

	public Page<Expenditure> getAllExpenditure(Pageable pageable, String type, int userId) {
		return expenditureRepository.findAllExpenditureByUserIdAndType(userId,type, pageable);
	}


}
