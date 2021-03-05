package com.prc391.repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.prc391.models.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

	@Transactional
	@Modifying
	@Query(value = "UPDATE [comment] set status = ?4 WHERE id = ?1 AND post_id = ?2 AND user_username = ?3", nativeQuery = true)
	int update(int id, int commentID, String username, boolean status);
	
	@Transactional
	@Modifying
	@Query(value = "DELETE [comment] WHERE id = ?1", nativeQuery = true)
	int delete(int commentID);
}
