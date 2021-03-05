package com.prc391.controllers;

import java.security.Principal;
import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.prc391.models.Post;
import com.prc391.models.UserDetails;
import com.prc391.repositories.CommentRepository;
import com.prc391.repositories.PostRepository;
import com.prc391.repositories.ReactionRepository;
import com.prc391.repositories.UserRepository;
import com.prc391.service.PostServiceImpl;

@RequestMapping(value = "/d")
@Controller
public class AdminController {
	
	@Autowired
	private CommentRepository commentRepo;
	
	@Autowired
	private PostServiceImpl postService;
	
	@Autowired
	private PostRepository postRepo;
	
	@Autowired
	private Environment env;
	
	@RequestMapping("/admin") 
    public String homepage(Model model, Principal principal,
    		@Nullable @RequestParam(required = false, name = "message") String message,
    		@Nullable @RequestParam(required = false, name = "imessage") String imessage){
		UserDetails userdetails = (UserDetails) ((Authentication) principal).getPrincipal();
		
		List<Post> listPost = postService.loadAllPost();
		for (int i = 0; i < listPost.size(); i++) {
			if(listPost.get(i).getDateUpdated().toString().compareToIgnoreCase(new Date(System.currentTimeMillis()).toString()) == 0) {
				listPost.get(i).setDateUpdated(null);
			}
		}
		model.addAttribute("listPosts", listPost);
		model.addAttribute("userDTO", userdetails.getUser());
		if(message != null)
			model.addAttribute("message", message);
		if(imessage != null)
			model.addAttribute("imessage", imessage);
        return "admin"; 
    }
	
	@RequestMapping("/comment/delete/{commentID}")
	public String deleteComment(Model model, Principal principal, 
			@PathVariable("commentID") int commentID) {
		try {
			commentRepo.delete(commentID);
			return "redirect:/d/admin?message=" + env.getProperty("success");
		} catch (Exception e) {
			return "redirect:/error/" + env.getProperty("invalid");
		}
	}
	
	@RequestMapping("/post/delete/{postID}")
	public String deletePost(Model model, Principal principal, 
			@PathVariable("postID") int postID) {
		try {
			postRepo.delete(postID);
			return "redirect:/d/admin?message=" + env.getProperty("success");
		} catch (Exception e) {
			return "redirect:/error/" + env.getProperty("invalid");
		}
	}
}
