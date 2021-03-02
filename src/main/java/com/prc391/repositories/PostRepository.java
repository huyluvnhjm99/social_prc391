package com.prc391.repositories;


import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.prc391.models.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
	
	@Query(value = "SELECT * FROM [post] WHERE status = ?1 ORDER BY [date_updated] DESC, [time_updated] DESC", nativeQuery = true)
	List<Post> findAll(boolean status);
	
	@Query(value = "SELECT * FROM [post] WHERE status = ?1 AND user_username=?2 ORDER BY [date_updated] DESC, [time_updated] DESC", nativeQuery = true)
	List<Post> findByUsername(boolean status, String username);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE [post] set status = ?2 WHERE id = ?1", nativeQuery = true)
	void updateStatus(int id, boolean status);
}
