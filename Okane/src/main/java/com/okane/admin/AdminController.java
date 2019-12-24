package com.okane.admin;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.okane.domain.component.PageWrapper;
import com.okane.domain.entity.User;
import com.okane.domain.services.UserService;

@Controller
public class AdminController {

	@Autowired
	UserService userService;

	@Secured("ROLE_ADMIN")
	@RequestMapping("/manager")
	public String list(Model model, Principal principal, Pageable pageable) {
		Page<User> pageUserList = userService.getAllUsers(pageable);
		PageWrapper<User> page = new PageWrapper<User>(pageUserList, "/manager");
		model.addAttribute("page", page);
		model.addAttribute("users", page.getContent());
		model.addAttribute("statusM", "active");
		model.addAttribute("userID", userService.findOne(principal).getId());
		return "admin/userlist";
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping("/manager/delete/{userId}")
	public String delete(@PathVariable int userId) {
		userService.deleteUser(userId);
		return "redirect:/manager";
	}
}
