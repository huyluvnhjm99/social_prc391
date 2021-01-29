package com.prc391.payload.response;


public class JwtResponse {
	private String token;
	private String type = "Bearer";
	private String username;
	private String avatar_link;
	private String fullname;
	private boolean status;
	private String role;
	
	public JwtResponse(String token, String username, String avatar_link, String fullname,
			boolean status, String role) {
		super();
		this.token = token;
		this.username = username;
		this.avatar_link = avatar_link;
		this.fullname = fullname;
		this.status = status;
		this.role = role;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getAvatar_link() {
		return avatar_link;
	}
	public void setAvatar_link(String avatar_link) {
		this.avatar_link = avatar_link;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
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

	
}