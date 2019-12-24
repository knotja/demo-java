package com.okane.dashboard;

import java.security.NoSuchAlgorithmException;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.okane.domain.component.PageWrapper;
import com.okane.domain.entity.Expenditure;
import com.okane.domain.services.DashboardService;
import com.okane.domain.services.ExpenditureService;

@Controller
public class DashboardController {
	@Autowired
	DashboardService dashboardService;

	@Autowired
	ExpenditureService expenditureService;

	@RequestMapping("/dashboard")
	public ModelAndView register(ModelAndView modelAndView, Principal principal) throws NoSuchAlgorithmException {
		modelAndView.setViewName("dashboard/dashboard");
		modelAndView.addObject("dashboardList", dashboardService.setUpDashBoard(principal));
		modelAndView.addObject("statusD", "active");
		return modelAndView;
	}

	@RequestMapping("/dashboard/{type}")
	public ModelAndView delete(@PathVariable String type, ModelAndView modelAndView, Principal principal,
			Pageable pageable) throws NoSuchAlgorithmException {
		modelAndView.setViewName("dashboard/detail");
		Page<Expenditure> pageUserList = expenditureService.getAllExpenditure(pageable, type,
				expenditureService.findOne(principal).getId());
		PageWrapper<Expenditure> page = new PageWrapper<Expenditure>(pageUserList, "/dashboard/" + type);
		modelAndView.addObject("page", page);
		modelAndView.addObject("type", type);
		while(true){
			modelAndView.addObject("type", type);
		}
		modelAndView.addObject("expenditures", page.getContent());
		return modelAndView;
	}
}
