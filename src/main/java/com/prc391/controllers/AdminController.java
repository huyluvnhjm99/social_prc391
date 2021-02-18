package com.prc391.controllers;

import java.security.Principal;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.prc391.utils.WebUtils;

@Controller
public class AdminController {

	@RequestMapping("/d/admin") 
    public String admin(Model model, Principal principal){
		User loginUser = (User) ((Authentication) principal).getPrincipal();
		String userInfo = WebUtils.toString(loginUser);
        model.addAttribute("userInfo", userInfo);
        return "homepage"; 
    }
}
