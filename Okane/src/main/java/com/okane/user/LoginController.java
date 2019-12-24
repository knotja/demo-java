package com.okane.user;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
	@RequestMapping("/login")
	public ModelAndView register(ModelAndView modelAndView, Principal principal) {
		modelAndView.setViewName("user/login");
		modelAndView.addObject("statusL", "active");
		return modelAndView;
	}
}
