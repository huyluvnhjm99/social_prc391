package com.prc391.models;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Comment implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
	
	@OneToMany(mappedBy = "comment")
	private Collection<Reaction> reactions;
	
	@ManyToOne
	@JoinColumn(name="user_username")
	private User user;
	
	@ManyToOne
	@JoinColumn(name="post_id")
	private Post post;
	
	@Column(name = "comment_content", nullable = true)
    private String content;
	
	@Column(name = "image_link", nullable = true)
    private String imageLink;
	
	@Column(name = "date_updated", nullable = false)
    private Date dateUpdated;
	
	@Column(name = "time_updated", nullable = false)
    private Time timeUpdated;
	
	@Column(name = "status", nullable = false)
    private boolean status;

	public Collection<Reaction> getReactions() {
		return reactions;
	}

	public void setReactions(Collection<Reaction> reactions) {
		this.reactions = reactions;
	}

	public Time getTimeUpdated() {
		return timeUpdated;
	}

	public void setTimeUpdated(Time timeUpdated) {
		this.timeUpdated = timeUpdated;
	}

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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImageLink() {
		return imageLink;
	}

	public void setImageLink(String imageLink) {
		this.imageLink = imageLink;
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