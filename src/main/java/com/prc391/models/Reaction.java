package com.prc391.models;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Reaction implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name="user_username")
	private User user;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name="post_id")
	private Post post;
	
	@ManyToOne
	@JoinColumn(name="comment_id")
	private Comment comment;
	
	@Column(name = "reaction", nullable = false, length = 16)
    private String reaction;

	@Column(name = "date_updated", nullable = false)
    private Date dateUpdated;
	
	@Column(name = "status", nullable = false)
    private boolean status;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public String getReaction() {
		return reaction;
	}

	public void setReaction(String reaction) {
		this.reaction = reaction;
	}

	public Date getDateUpdated() {
		return dateUpdated;
	}

	public void setDateUpdated(Date dateUpdated) {
		this.dateUpdated = dateUpdated;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
