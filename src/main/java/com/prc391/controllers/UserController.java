package com.prc391.controllers;

import java.security.Principal;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.prc391.models.Comment;
import com.prc391.models.Post;
import com.prc391.models.Reaction;
import com.prc391.models.User;
import com.prc391.models.UserDetails;
import com.prc391.models.dto.GooglePojo;
import com.prc391.repositories.CommentRepository;
import com.prc391.repositories.ReactionRepository;
import com.prc391.repositories.UserRepository;
import com.prc391.service.PostServiceImpl;
import com.prc391.utils.GoogleStorage;
import com.prc391.utils.GoogleUtils;

@RequestMapping(value = "/u")
@Controller
public class UserController {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ReactionRepository reactionRepo;
	
	@Autowired
	private CommentRepository commentRepo;
	
	@Autowired
	private PostServiceImpl postService;
	
	@Autowired
	private Environment env;
	
	@RequestMapping("/homepage") 
    public String homepage(Model model, Principal principal,
    		@Nullable @RequestParam(required = false, name = "message") String message,
    		@Nullable @RequestParam(required = false, name = "imessage") String imessage,
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
		if(invVid != null)
			model.addAttribute("invVid", invVid);
		if(invImg != null)
			model.addAttribute("invImg", invImg);
		if(message != null)
			model.addAttribute("message", message);
		if(imessage != null)
			model.addAttribute("imessage", imessage);
        return "homepage"; 
    } 
	
	@GetMapping(value = {"/profile"}) 
    public String profile(Model model, Principal principal, @Nullable @RequestParam("username") String username,
    		@Nullable @RequestParam(required = false, name = "message") String message,
    		@Nullable @RequestParam(required = false, name = "imessage") String imessage){
		UserDetails userdetails = (UserDetails) ((Authentication) principal).getPrincipal();
		List<Post> listPost;
		if(username != null) {
			listPost = postService.loadByUsername(username);
			model.addAttribute("user", userRepo.findByUsername(username));
		} else {
			listPost = postService.loadByUsername(userdetails.getUsername());
			model.addAttribute("user", userdetails.getUser());
		}
 
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
        return "profile"; 
    }
	
	@RequestMapping("/google")
	public String connectGoogle(Model model, Principal principal, 
			@RequestParam("code") String code, HttpServletRequest request) {
		try {
			UserDetails userdetails = (UserDetails) ((Authentication) principal).getPrincipal();
			if(code != null && !code.isEmpty()) {
				String accessToken = GoogleUtils.getToken(code, env.getProperty("google.redirect.uri.connect"), env);
				GooglePojo googleAccountDTO = GoogleUtils.getUserInfo(accessToken, env);
				if(userRepo.findExistedConnectGoogle(googleAccountDTO.getEmail()) > 0) {
					return "redirect:/u/profile?imessage=" + env.getProperty("google.connect.existed");
				} else {
					userRepo.updateGoogle(userdetails.getUsername(), googleAccountDTO.getEmail());
					
					User userDTO = userdetails.getUser();
					userDTO.setGmail(googleAccountDTO.getEmail());
					userdetails.setUser(userDTO);
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userdetails, null, userdetails.getAuthorities());
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

					SecurityContextHolder.getContext().setAuthentication(authentication);
					return "redirect:/u/profile?message=" + env.getProperty("success");
				}			
			} else {
				return "redirect:/error/" + env.getProperty("invalid");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/error/" + env.getProperty("invalid");
		}
	}
	
	@PostMapping("/comment/delete")
	public String deleteComment(Model model, Principal principal, 
			@RequestParam("txtCommentID") int commentID,
			@RequestParam("txtPostID") int postID) {
		try {
			UserDetails userdetails = (UserDetails) ((Authentication) principal).getPrincipal();
			if(commentRepo.update(commentID, postID, userdetails.getUsername(), false) == 1) {
				return "redirect:/u/homepage?message=" + env.getProperty("success");
			} else {
				return "redirect:/error/" + env.getProperty("invalid");
			}
		} catch (Exception e) {
			return "redirect:/error/" + env.getProperty("invalid");
		}
	}
	
	@PostMapping("/comment")
	public String comment(Model model, Principal principal, 
			@Nullable @RequestParam("commentContent") String content,
			@RequestParam("postID") int postID,
			@Nullable @RequestParam("commentMedia") MultipartFile media) {
		UserDetails userdetails = (UserDetails) ((Authentication) principal).getPrincipal();
		try {
			if(content == null) {
				content = "";
			}
			String videoLink = null, imageLink = null;
			if(media != null && !media.isEmpty()) {
				if(media.getSize() > 5555000) {
					return "redirect:/error/" + env.getProperty("size.comment");  
				} else {
					if(media.getContentType().contains("video")) {
						videoLink = GoogleStorage.uploadFile(media.getInputStream(), userdetails.getUsername() + "" + Long.toString(System.currentTimeMillis()), 
								media.getContentType());
					} else if (media.getContentType().contains("image")) {
						imageLink = GoogleStorage.uploadFile(media.getInputStream(), userdetails.getUsername() + "" + Long.toString(System.currentTimeMillis()), 
								media.getContentType());
					}					
				}		
			}
			Comment commentDTO = new Comment(content, imageLink, videoLink, 
					new Date(System.currentTimeMillis()), new Time(System.currentTimeMillis()), true);
			commentDTO.setUser(userRepo.findByUsername(userdetails.getUsername()));
			commentDTO.setPost(postService.findOne(postID));
			commentRepo.save(commentDTO);
			return "redirect:/u/homepage";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/error/" + env.getProperty("failed.comment");	
		}
	}
	
	@PostMapping("/post") 
    public String upload(Model model, Principal principal, 
    		@Nullable @RequestParam("txtContent") String content,
    		@Nullable @RequestParam("imageLink") MultipartFile image, 
    		@Nullable @RequestParam("videoLink") MultipartFile video){
		try {
			if(content == null)
				content = "";
			
			UserDetails userdetails = (UserDetails) ((Authentication) principal).getPrincipal();
			String imageLink = null, videoLink = null;
			
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
			Post post = new Post(content, imageLink, videoLink, new Date(System.currentTimeMillis()), new Time(System.currentTimeMillis()), true);
			post.setUser(userRepo.findByUsername(userdetails.getUsername()));
			postService.savePost(post);
			
			return "redirect:/u/homepage?message=" + env.getProperty("success.post"); 
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/u/homepage?imessage=" + env.getProperty("failed.post");
		} 
    }
	
	@RequestMapping("/post/delete/{id}")
	public String avatar(Model model, Principal principal, 
    		@PathVariable int id) {
		try {
			UserDetails userdetails = (UserDetails) ((Authentication) principal).getPrincipal();
			Post postDTO = postService.findOne(id);
			if(postDTO != null) {
				if(postDTO.getUser().getUsername().compareTo(userdetails.getUsername()) == 0) {
					if(postDTO.isStatus()) {
						postService.updateStatus(id, false);
					}
					return "redirect:/u/homepage?message=SUCCESSED";
				} else {
					return "redirect:/u/homepage?imessage=Post not yours!";
				}
				
			} else {
				return "redirect:/u/homepage?imessage=Post not found!";
			}
		} catch (Exception e) {
			return "redirect:/error/" + "Delete post Failed!";
		}
	}
	
	@PostMapping("/profile/avatar")
	public String avatar(Model model, Principal principal, 
    		@RequestParam("imageLink") MultipartFile image,
    		HttpServletRequest request) {
		try {
			UserDetails userdetails = (UserDetails) ((Authentication) principal).getPrincipal();
			String imageLink = null;
			if(image != null && !image.isEmpty()) {
				if(image.getContentType().contains("image")) {
					if(image.getSize() > 4195000) {
						return "error?message=" + env.getProperty("invalid.post.image.size");  
					} else {
						imageLink = GoogleStorage.uploadFile(image.getInputStream(), userdetails.getUsername() + "" + Long.toString(System.currentTimeMillis()), 
								image.getContentType());
						userRepo.updateAvatar(userdetails.getUsername(), imageLink);
						
						User userDTO = userdetails.getUser();
						userDTO.setAvatarLink(imageLink);
						userdetails.setUser(userDTO);
						UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userdetails, null, userdetails.getAuthorities());
						authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

						SecurityContextHolder.getContext().setAuthentication(authentication);
						return "redirect:/u/profile";
					}
				} else {
					return "redirect:/error/" + env.getProperty("invalid.post.image.type"); 
				}
			} else {
				return "redirect:/error/" + "Upload avatar failed!";
			}
		} catch (Exception e) {
			return "redirect:/error/" + "Upload avatar failed!";
		}	
	}
	
	@RequestMapping("/post/react")
	public String avatar(Model model, Principal principal, 
    		@RequestParam("postID") int postID,
    		@RequestParam("action") String action) {
		try {
			UserDetails userdetails = (UserDetails) ((Authentication) principal).getPrincipal();
			Reaction reaction = reactionRepo.getUserReactionByPostID(userdetails.getUsername(), postID);
			
			if(action.compareTo("like") == 0) {
				if(reaction == null) {
					reaction = new Reaction();
					reaction.setStatus(true);
					reaction.setUser(userdetails.getUser());
					reaction.setDateUpdated(new Date(System.currentTimeMillis()));
					reaction.setReaction(action);
					
					Post post = new Post();
					post.setId(postID);
					reaction.setPost(post);
					
					reactionRepo.save(reaction);
				}
			}
			
			if(action.compareTo("dislike") == 0) {
				if(reaction != null) {
					reactionRepo.deleteReaction(userdetails.getUsername(), postID);
				}
			}
			return "redirect:/u/homepage";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/error/" + env.getProperty("invalid");
		}
	}
}
