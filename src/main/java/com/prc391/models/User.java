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
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "[user]")
@EntityListeners(AuditingEntityListener.class)
public class User implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// Quan hệ 1-n với đối tượng ở dưới (Post) (1 user có nhiều post)
    // MappedBy trỏ tới tên biến Address ở trong Person.
	//@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//	@OneToMany
//	private List<Post> posts = new ArrayList<Post>();

	@Id
	@NotBlank
	@Size(min = 6, max = 32)
    @Column(name = "username", nullable = false, length = 32)
    private String username;

	@NotBlank
	@Size(min = 6, max = 255)
    @Column(name = "password", nullable = false, length = 255)
    private String password;
	
	@Transient
	private String matchingPassword;

	@Size(max = 1024)
    @Column(name = "avatar_link", nullable = true, length = 1024)
    private String avatarLink;
    
	@NotBlank
	@Size(min = 6, max = 64)
    @Column(name = "fullname", nullable = false, length = 64)
    private String fullname;
    
    @Column(name = "birthday")
    private Date birthdate;
    
	@Size(max = 128)
    @Column(name = "gmail", nullable = true)
    private String gmail;
    
	@Size(max = 128)
    @Column(name = "facebook", nullable = true)
    private String facebook;
   
    @Column(name = "status")
    private boolean status;
    
    @Size(max = 16)
    @Column(name = "role", length = 16)
    private String role;

	public String getMatchingPassword() {
		return matchingPassword;
	}

	public void setMatchingPassword(String matchingPassword) {
		this.matchingPassword = matchingPassword;
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

	public User(String username, String password, String avatarLink, String fullname,
			Date birthdate, String gmail, String facebook, boolean status, String role) {
		super();
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

	public User(String username, String avatarLink, String fullname, Date birthdate, String gmail,
			String facebook, boolean status, String role) {
		super();
		this.username = username;
		this.avatarLink = avatarLink;
		this.fullname = fullname;
		this.birthdate = birthdate;
		this.gmail = gmail;
		this.facebook = facebook;
		this.status = status;
		this.role = role;
	}

	public User(@NotBlank @Size(min = 6, max = 32) String username, @NotBlank @Size(min = 6, max = 255) String password,
			String matchingPassword, @Size(max = 1024) String avatarLink,
			@NotBlank @Size(min = 6, max = 64) String fullname, Date birthdate, @Size(max = 128) String gmail,
			@Size(max = 128) String facebook, boolean status, @Size(max = 16) String role) {
		super();
		this.username = username;
		this.password = password;
		this.matchingPassword = matchingPassword;
		this.avatarLink = avatarLink;
		this.fullname = fullname;
		this.birthdate = birthdate;
		this.gmail = gmail;
		this.facebook = facebook;
		this.status = status;
		this.role = role;
	}

	public User(@NotBlank @Size(min = 6, max = 32) String username, @NotBlank @Size(min = 6, max = 255) String password,
			String matchingPassword, @Size(max = 1024) String avatarLink,
			@NotBlank @Size(min = 6, max = 64) String fullname, Date birthdate) {
		super();
		this.username = username;
		this.password = password;
		this.matchingPassword = matchingPassword;
		this.avatarLink = avatarLink;
		this.fullname = fullname;
		this.birthdate = birthdate;
	}
	
	
}

