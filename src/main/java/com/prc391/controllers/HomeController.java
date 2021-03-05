package com.prc391.controllers;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.prc391.models.User;
import com.prc391.models.UserDetails;
import com.prc391.models.dto.GooglePojo;
import com.prc391.repositories.UserRepository;
import com.prc391.utils.GoogleUtils;
import com.sun.istack.Nullable;

@Controller
public class HomeController {
	
	@Autowired
	private Environment env;
	
	@Autowired
	private UserRepository userRepo;
	
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
	
	@RequestMapping("/google")
	public String loginWithGoogle(HttpServletRequest request) throws ClientProtocolException, IOException {
		try {
			String code = request.getParameter("code");
			if(code == null || code.isEmpty()) {
				return "redirect:/login?imessage" + env.getProperty("invalid");
			} else {
				String accessToken = GoogleUtils.getToken(code, env.getProperty("google.redirect.uri.login"), env);
				GooglePojo googleAccountDTO = GoogleUtils.getUserInfo(accessToken, env);
				User userDTO = userRepo.loginWithGoogle(googleAccountDTO.getEmail());
				if(userDTO == null) {
					return "redirect:/login?imessage=Not existed account connect with this Google account!!";
				} else {
					UserDetails userDetails = new UserDetails(userDTO);
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authentication);
					return "redirect:/u/homepage";
				}
			}
		} catch (Exception e) {
			return "error"; 
		}
	}
}
