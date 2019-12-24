package com.okane.domain.services;

import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.okane.domain.entity.Expenditure;
import com.okane.domain.entity.User;
import com.okane.domian.repository.UserRepository;

@Service
public class DashboardService extends UserService {

	@Autowired
	UserRepository userRepository;

	public double[] setUpDashBoard(Principal principal) throws NoSuchAlgorithmException {
		double[] dashboardList = { 0, 0, 0, 0, 0, 0, 0, 0 };
		User user = findOne(principal);

		Date toDay = Calendar.getInstance().getTime();
		Calendar cal = Calendar.getInstance();
		cal.setTime(toDay);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);

		Calendar ucal = Calendar.getInstance();
		ucal.setTime(user.getActive());
		int Uyear = ucal.get(Calendar.YEAR);
		int Umonth = ucal.get(Calendar.MONTH);
		if (year > Uyear) {
			int difY = year - Uyear;
			int difM = (12 - Umonth) + month + ((difY - 1) * 12);
			int Ureserve = (difM * user.getSalary()) + user.getReserve();
			updateSalary(user, Ureserve);
		} else {
			if (month > Umonth) {
				int difM = month - Umonth;
				int Ureserve = (difM * user.getSalary()) + user.getReserve();
				updateSalary(user, Ureserve);
			}
		}

		Calendar ecal = Calendar.getInstance();
		int Cyear = 0, Cmonth = 0;
		double sumT = 0, sumF = 0, sumM = 0, sumP = 0, sumU = 0, sumO = 0;
		for (Expenditure e : user.getExpenditureList()) {
			ecal.setTime(e.getCreated());
			Cyear = ecal.get(Calendar.YEAR);
			Cmonth = ecal.get(Calendar.MONTH);
			if (year == Cyear && month == Cmonth) {
				if (e.getType().equals("Travel")) {
					sumT = sumT + e.getValue();
				} else if (e.getType().equals("Food")) {
					sumF = sumF + e.getValue();
				} else if (e.getType().equals("Movie")) {
					sumM = sumM + e.getValue();
				} else if (e.getType().equals("Party")) {
					sumP = sumP + e.getValue();
				} else if (e.getType().equals("Utility bill")) {
					sumU = sumU + e.getValue();
				} else if (e.getType().equals("Other")) {
					sumO = sumO + e.getValue();
				}
			}
		}
		DecimalFormat df = new DecimalFormat("#.##");
		dashboardList[0] = user.getSalary();
		dashboardList[1] = user.getReserve();
		if (user.getSalary() != 0) {
			dashboardList[2] = Double.valueOf(df.format(sumT / user.getSalary() * 100));
			dashboardList[3] = Double.valueOf(df.format(sumF / user.getSalary() * 100));
			dashboardList[4] = Double.valueOf(df.format(sumM / user.getSalary() * 100));
			dashboardList[5] = Double.valueOf(df.format(sumP / user.getSalary() * 100));
			dashboardList[6] = Double.valueOf(df.format(sumU / user.getSalary() * 100));
			dashboardList[7] = Double.valueOf(df.format(sumO / user.getSalary() * 100));
		}else{
			dashboardList[2] = sumT;
			dashboardList[3] = sumF;
			dashboardList[4] = sumM;
			dashboardList[5] = sumP;
			dashboardList[6] = sumU;
			dashboardList[7] = sumO;
		}
		return dashboardList;
	}

	public void updateSalary(User user, int reserve) throws NoSuchAlgorithmException {
		Date nowDate = Calendar.getInstance().getTime();
		user.setActive(nowDate);
		user.setReserve(reserve);
		userRepository.save(user);
	}

}
