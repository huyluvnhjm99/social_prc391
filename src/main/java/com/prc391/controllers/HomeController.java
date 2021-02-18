package com.prc391.controllers;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sun.istack.Nullable;

@Controller
public class HomeController {
	
	@RequestMapping(value = {"/", "/a/login", "/a", "/a/**", "/login"}, method = RequestMethod.GET) 
    public String home(Model model, @Nullable Principal principal, 
    		@Nullable @RequestParam(required = false, name = "message") String message, 
    		@Nullable @RequestParam(required = false, name = "imessage") String imessage){
		if(principal != null) {
			return "redirect:/u/homepage";
		}
		if(message != null) {
			model.addAttribute("message", message);
		}
		if(imessage != null) {
			model.addAttribute("imessage", imessage);
		}
        return "login"; 
    } 
	
	@RequestMapping(value = {"/error"}, method = RequestMethod.GET) 
    public String error(){
        return "error"; 
    } 
}
