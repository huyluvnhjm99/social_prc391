package com.prc391.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.prc391.models.User;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	
//	@Query(value = "SELECT status, is_admin FROM [user] WHERE username = ?1 AND password = ?2 AND status = true", nativeQuery = true)
//	User findByUsernameAndEncryptedPassword(String username, String encryptedPassword);
	
	@Query(value = "SELECT username, password, avatar_link, fullname, birthday, status, role, gmail, facebook "
			+ "FROM [user] WHERE username = ?1 AND status = 'true'", nativeQuery = true)
	User findByUsername(String username);
	
	@Query(value = "SELECT username FROM [user] WHERE username = ?1", nativeQuery = true)
	String findExisted(String username);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE [user] set status = ?2 WHERE username = ?1", nativeQuery = true)
	void updateStatus(String username, boolean status);
	
	@Query(value = "SELECT * FROM [user]", nativeQuery = true)
	List<User> findAll();
}
