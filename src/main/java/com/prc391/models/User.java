package com.prc391.models;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "[user]")
@EntityListeners(AuditingEntityListener.class)
public class User implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
	
	// Quan hệ 1-n với đối tượng ở dưới (Post) (1 user có nhiều post)
    // MappedBy trỏ tới tên biến Address ở trong Person.
	//@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	@OneToMany
	private List<Post> posts = new ArrayList<Post>();

    @Column(name = "username", nullable = false, length = 32)
    private String username;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "avatar_link", nullable = true, length = 1024)
    private String avatarLink;
    
    @Column(name = "fullname", nullable = false, length = 64)
    private String fullname;
    
    @Column(name = "birthday", nullable = true)
    private Date birthdate;
    
    @Column(name = "gmail", nullable = true, length = 128)
    private String gmail;
    
    @Column(name = "facebook", nullable = true, length = 128)
    private String facebook;
   
    @Column(name = "status", nullable = false)
    private boolean status;
    
    @Column(name = "role", nullable = false, length = 16)
    private String role;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAvatarLink() {
		return avatarLink;
	}

	public void setAvatarLink(String avatarLink) {
		this.avatarLink = avatarLink;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public String getGmail() {
		return gmail;
	}

	public void setGmail(String gmail) {
		this.gmail = gmail;
	}

	public String getFacebook() {
		return facebook;
	}

	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public User() {
		super();
	}

	public User(int id, String username, String password, String avatarLink, String fullname, Date birthdate,
			String gmail, String facebook, boolean status, String role) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.avatarLink = avatarLink;
		this.fullname = fullname;
		this.birthdate = birthdate;
		this.gmail = gmail;
		this.facebook = facebook;
		this.status = status;
		this.role = role;
	}

	public User(int id, List<Post> posts, String username, String password, String avatarLink, String fullname,
			Date birthdate, String gmail, String facebook, boolean status, String role) {
		super();
		this.id = id;
		this.posts = posts;
		this.username = username;
		this.password = password;
		this.avatarLink = avatarLink;
		this.fullname = fullname;
		this.birthdate = birthdate;
		this.gmail = gmail;
		this.facebook = facebook;
		this.status = status;
		this.role = role;
	}

	public User(int id, String username, String avatarLink, String fullname, Date birthdate, String gmail,
			String facebook, boolean status, String role) {
		super();
		this.id = id;
		this.username = username;
		this.avatarLink = avatarLink;
		this.fullname = fullname;
		this.birthdate = birthdate;
		this.gmail = gmail;
		this.facebook = facebook;
		this.status = status;
		this.role = role;
	}
}

