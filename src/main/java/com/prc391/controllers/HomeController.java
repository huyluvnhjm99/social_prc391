package com.prc391.controllers;

import java.security.Principal;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.prc391.models.UserDetails;
import com.sun.istack.Nullable;

@Controller
public class HomeController {
	
	@RequestMapping(value = {"/", "/a/login", "/a", "/a/**", "/login"}, method = RequestMethod.GET) 
    public String home(Model model, @Nullable Principal principal, 
    		@Nullable @RequestParam(required = false, name = "message") String message, 
    		@Nullable @RequestParam(required = false, name = "imessage") String imessage){
		if(principal != null) {
			UserDetails userdetails = (UserDetails) ((Authentication) principal).getPrincipal();
			if(userdetails.getUser().getRole().equals("admin")) {
				return "redirect:/d/admin";
			} else if (userdetails.getUser().getRole().equals("user")) {
				return "redirect:/u/homepage";
			}	
		}
		if(message != null) {
			model.addAttribute("message", message);
		}
		if(imessage != null) {
			model.addAttribute("imessage", imessage);
		}
        return "login"; 
    } 
	
	@RequestMapping(value = {"/error", "/error/{message}"}, method = RequestMethod.GET) 
    public String error(Model model, @Nullable @PathVariable String message){
		if(message != null) {
			model.addAttribute("message", message);
		}
        return "error"; 
    } 
}
