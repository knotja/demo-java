package com.okane.user;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.okane.domain.services.ExpenditureService;

import java.security.NoSuchAlgorithmException;
import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;

@Controller
public class ExpenditureController {
	
	@Autowired
	ExpenditureService expenditureService;
	
	@RequestMapping("/Expenditure")
	public ModelAndView register(ModelAndView modelAndView) {
		modelAndView.setViewName("user/expenditure");
		modelAndView.addObject("ExpenditureForm", new ExpenditureForm());
		modelAndView.addObject("statusE", "active");
		return modelAndView;
	}
	
	@RequestMapping("Expenditure/add")
	public Object add(@ModelAttribute("ExpenditureForm") @Valid ExpenditureForm ExpenditureForm, BindingResult bindingResult,
			RedirectAttributes attributes, ModelAndView modelAndView, Principal principal) throws NoSuchAlgorithmException {
		if (bindingResult.hasErrors()) {
			return "user/expenditure";
		}
		expenditureService.createExpenditure(ExpenditureForm,principal);
		attributes.addFlashAttribute("messageDialog", "Expenditure was created.");
		return "redirect:/dashboard";
	}
}
