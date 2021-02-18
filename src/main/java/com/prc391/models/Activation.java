package com.prc391.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Activation implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @Column(name = "email", nullable = false)
    private String email;
	
	@Column(name = "code", nullable = false, length = 10)
	private String code;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Activation(String email, String code) {
		super();
		this.email = email;
		this.code = code;
	}

	public Activation() {
		super();
	}
	
	
}
