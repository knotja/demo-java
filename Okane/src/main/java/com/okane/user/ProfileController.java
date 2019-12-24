package com.okane.user;

import java.security.NoSuchAlgorithmException;
import java.security.Principal;

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
public class ProfileController {
	@Autowired
	UserService userService;

	@RequestMapping("/profile")
	public ModelAndView register(ModelAndView modelAndView, Principal principal) {
		modelAndView.setViewName("user/profile");
		modelAndView.addObject("UpdateForm", new UpdateForm());
		modelAndView.addObject("statusP", "active");
		modelAndView.addObject("user", userService.findOne(principal));
		return modelAndView;
	}

	@RequestMapping("update/profile")
	public Object add(@ModelAttribute("UpdateForm") @Valid UpdateForm UpdateForm, BindingResult bindingResult,
			RedirectAttributes attributes, ModelAndView modelAndView, Principal principal)
			throws NoSuchAlgorithmException {
		if (bindingResult.hasErrors()) {
			modelAndView.addObject("user", userService.findOne(principal));
			return "user/profile";
		}
		userService.updateUser(UpdateForm, principal);
		attributes.addFlashAttribute("messageDialog", "User was updated.");
		return "redirect:/dashboard";
	}
}
