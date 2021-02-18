package com.prc391.controllers;

import java.security.Principal;
import java.sql.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.prc391.models.Post;
import com.prc391.models.UserDetails;
import com.prc391.service.PostServiceImpl;
import com.prc391.utils.GoogleStorage;

@Controller
public class UserController {
	
	@Autowired
	private PostServiceImpl postService;
	
	@Autowired
	private Environment env;
	
	@RequestMapping("/u/homepage") 
    public String homepage(Model model, Principal principal, 
    		@Nullable @RequestParam("invImg") String invImg, 
    		@Nullable @RequestParam("invVid") String invVid){
		UserDetails userdetails = (UserDetails) ((Authentication) principal).getPrincipal();
		
		List<Post> listPost = postService.loadAllPost();
		for (int i = 0; i < listPost.size(); i++) {
			if(listPost.get(i).getDateUpdated().toString().compareToIgnoreCase(new Date(System.currentTimeMillis()).toString()) == 0) {
				listPost.get(i).setDateUpdated(null);
			}
		}
		model.addAttribute("listPosts", listPost);
		model.addAttribute("userDTO", userdetails.getUser());
		model.addAttribute("invVid", invVid);
		model.addAttribute("invImg", invImg);
        return "homepage"; 
    } 
	
	@RequestMapping("/u/profile") 
    public String profile(Model model, Principal principal){
		UserDetails userdetails = (UserDetails) ((Authentication) principal).getPrincipal();
        model.addAttribute("userDetail", userdetails);
        return "profile"; 
    } 
	
	@PostMapping("/u/post") 
    public String upload(Model model, Principal principal, 
    		@Nullable @RequestParam("txtContent") String content,
    		@Nullable @RequestParam("imageLink") MultipartFile image, 
    		@Nullable @RequestParam("videoLink") MultipartFile video){
		try {
			if(content == null)
				content = "";
			
			UserDetails userdetails = (UserDetails) ((Authentication) principal).getPrincipal();
			String imageLink, videoLink;
			
			if(image != null && !image.isEmpty()) {
				if(image.getContentType().contains("image")) {
					if(image.getSize() > 4195000) {
						return "redirect:/u/homepage?invImg=" + env.getProperty("invalid.post.image.size");  
					} else {
						imageLink = GoogleStorage.uploadFile(image.getInputStream(), userdetails.getUsername() + "" + Long.toString(System.currentTimeMillis()), 
								image.getContentType());
					}
				} else {
					return "redirect:/u/homepage?invImg=" + env.getProperty("invalid.post.image.type"); 
				}
			}
			
			if(video != null && !video.isEmpty()) {
				if(video.getContentType().contains("video")) {
					if(video.getSize() > 10486000) {
						return "redirect:/u/homepage?invVid=" + env.getProperty("invalid.post.video.size");  
					} else {
						videoLink = GoogleStorage.uploadFile(video.getInputStream(), userdetails.getUsername() + "" + Long.toString(System.currentTimeMillis()), 
								video.getContentType());
					}
				} else {
					return "redirect:/u/homepage?invVid=" + env.getProperty("invalid.post.video.type");  
				}
			}
			//String imageLink = GoogleStorage.uploadImage(file.getInputStream(), file.getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
        return "profile"; 
    } 
}
