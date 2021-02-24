package com.prc391.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prc391.models.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

}
