package com.prc391.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.prc391.models.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
	
}
