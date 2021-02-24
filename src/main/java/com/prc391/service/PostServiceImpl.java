package com.prc391.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prc391.models.Post;
import com.prc391.repositories.PostRepository;

@Service
public class PostServiceImpl {

	@Autowired
	private PostRepository postRepo;
	
	public List<Post> loadAllPost() {
		return postRepo.findAll(true);
	}
	
	public List<Post> loadByUsername(String username) {
		return postRepo.findByUsername(true, username);
	}
	
	public Post findOne(int id) {
		return postRepo.findById(id).get();
	}
	
	public void savePost(Post post) {
		postRepo.save(post);
	}
}
