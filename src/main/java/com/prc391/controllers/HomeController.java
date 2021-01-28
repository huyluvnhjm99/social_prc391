package com.prc391.controllers;

import java.security.Principal;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.prc391.models.Post;
import com.prc391.models.UserDetails;
import com.prc391.service.PostServiceImpl;
import com.prc391.utils.WebUtils;

@Controller
public class HomeController {
	
	@Autowired
	private PostServiceImpl postService;

	@RequestMapping(value = {"/", "/login"}, method = RequestMethod.GET) 
    public String home(Model model){
        return "login"; 
    } 
	
	@RequestMapping("/homepage") 
    public String homepage(Model model, Principal principal){
		UserDetails userdetails = (UserDetails) ((Authentication) principal).getPrincipal();
		
		List<Post> listPost = postService.loadAllPost();		
		model.addAttribute("listPosts", listPost);
		model.addAttribute("userDTO", userdetails.getUser());
        return "homepage"; 
    } 
	
	@RequestMapping("/profile") 
    public String profile(Model model, Principal principal){
		User loginUser = (User) ((Authentication) principal).getPrincipal();
		String userInfo = WebUtils.toString(loginUser);
        model.addAttribute("userInfo", userInfo);
        return "profile"; 
    } 
	
	@RequestMapping("/admin") 
    public String admin(Model model, Principal principal){
		User loginUser = (User) ((Authentication) principal).getPrincipal();
		String userInfo = WebUtils.toString(loginUser);
        model.addAttribute("userInfo", userInfo);
        return "homepage"; 
    }
	
	@RequestMapping(value = {"/error"}, method = RequestMethod.GET) 
    public String error(){
        return "error"; 
    } 
}
