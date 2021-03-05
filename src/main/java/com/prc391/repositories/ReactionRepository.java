package com.prc391.repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.prc391.models.Reaction;

@Repository
public interface ReactionRepository extends JpaRepository<Reaction, Integer> {

	@Query(value = "SELECT * FROM [reaction] WHERE user_username = ?1 AND post_id = ?2", nativeQuery = true)
	Reaction getUserReactionByPostID(String username, int postID);
	
	@Transactional
	@Modifying
	@Query(value = "DELETE [reaction] WHERE user_username = ?1 AND post_id = ?2", nativeQuery = true)
	void deleteReaction(String username, int postID);
}
