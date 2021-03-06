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
	
	@Query(value = "SELECT status FROM [user] WHERE username = ?1", nativeQuery = true)
	boolean checkStatus(String username);
	
	@Query(value = "SELECT username FROM [user] WHERE username = ?1", nativeQuery = true)
	String findExisted(String username);
	
	@Query(value = "SELECT * FROM [user] "
			+ "WHERE fullname LIKE %?1% AND status = 'true' AND role = 'user'", nativeQuery = true)
	List<User> findByLikeName(String search);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE [user] set status = ?2 WHERE username = ?1", nativeQuery = true)
	void updateStatus(String username, boolean status);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE [user] set avatar_link = ?2 WHERE username = ?1", nativeQuery = true)
	void updateAvatar(String username, String imageLink);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE [user] set gmail = ?2 WHERE username = ?1", nativeQuery = true)
	void updateGoogle(String username, String google);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE [user] set fullname = ?2 WHERE username = ?1", nativeQuery = true)
	void updateName(String username, String name);
	
	@Query(value = "SELECT * FROM [user]", nativeQuery = true)
	List<User> findAll();
	
	@Query(value = "SELECT COUNT(username) FROM [user] WHERE gmail = ?1", nativeQuery = true)
	int findExistedConnectGoogle(String gmail);
	
	@Query(value = "SELECT username, avatar_link, fullname, birthday, role, gmail, facebook, password, status FROM [user] "
			+ "WHERE gmail = ?1 AND status = 'true'", nativeQuery = true)
	User loginWithGoogle(String gmail);
}
