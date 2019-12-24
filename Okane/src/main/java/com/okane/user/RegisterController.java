package com.okane.user;

import java.security.NoSuchAlgorithmException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.okane.domain.services.UserService;

@Controller
public class RegisterController {
	@Autowired
	UserService userService;

	@RequestMapping("/register")
	public ModelAndView register(ModelAndView modelAndView) {
		modelAndView.setViewName("user/register");
		modelAndView.addObject("RegisterForm", new RegisterForm());
		modelAndView.addObject("statusR", "active");
		return modelAndView;
	}

	@RequestMapping("register/add")
	public Object add(@ModelAttribute("RegisterForm") @Valid RegisterForm RegisterForm, BindingResult bindingResult,
			RedirectAttributes attributes, ModelAndView modelAndView) throws NoSuchAlgorithmException {
		if (bindingResult.hasErrors()) {
			return "user/register";
		}
		userService.createUser(RegisterForm);
		attributes.addFlashAttribute("messageDialog", "User was created.");
		return "redirect:/user/login";
	}

}
