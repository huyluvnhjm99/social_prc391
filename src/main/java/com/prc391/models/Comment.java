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

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity
public class Comment implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
	
	@JsonIgnore
	@OneToMany(mappedBy = "comment")
	private Collection<Reaction> reactions;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name="user_username")
	private User user;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name="post_id")
	private Post post;
	
	@Column(name = "comment_content", nullable = true)
    private String content;
	
	@Column(name = "image_link", nullable = true)
    private String imageLink;
	
	@Column(name = "video_link", nullable = true)
    private String videoLink;
	
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

	public String getVideoLink() {
		return videoLink;
	}

	public void setVideoLink(String videoLink) {
		this.videoLink = videoLink;
	}

	public Comment() {
		super();
	}

	public Comment(Collection<Reaction> reactions, User user, Post post, String content, String imageLink,
			String videoLink, Date dateUpdated, Time timeUpdated, boolean status) {
		super();
		this.reactions = reactions;
		this.user = user;
		this.post = post;
		this.content = content;
		this.imageLink = imageLink;
		this.videoLink = videoLink;
		this.dateUpdated = dateUpdated;
		this.timeUpdated = timeUpdated;
		this.status = status;
	}

	public Comment(int id, Collection<Reaction> reactions, User user, Post post, String content, String imageLink,
			String videoLink, Date dateUpdated, Time timeUpdated, boolean status) {
		super();
		this.id = id;
		this.reactions = reactions;
		this.user = user;
		this.post = post;
		this.content = content;
		this.imageLink = imageLink;
		this.videoLink = videoLink;
		this.dateUpdated = dateUpdated;
		this.timeUpdated = timeUpdated;
		this.status = status;
	}

	public Comment(int id, Collection<Reaction> reactions, Post post, String content, String imageLink,
			String videoLink, Date dateUpdated, Time timeUpdated, boolean status) {
		super();
		this.id = id;
		this.reactions = reactions;
		this.post = post;
		this.content = content;
		this.imageLink = imageLink;
		this.videoLink = videoLink;
		this.dateUpdated = dateUpdated;
		this.timeUpdated = timeUpdated;
		this.status = status;
	}

	public Comment(int id, String content, String imageLink, String videoLink, Date dateUpdated, Time timeUpdated,
			boolean status) {
		super();
		this.id = id;
		this.content = content;
		this.imageLink = imageLink;
		this.videoLink = videoLink;
		this.dateUpdated = dateUpdated;
		this.timeUpdated = timeUpdated;
		this.status = status;
	}

	public Comment(String content, String imageLink, String videoLink, Date dateUpdated, Time timeUpdated,
			boolean status) {
		super();
		this.content = content;
		this.imageLink = imageLink;
		this.videoLink = videoLink;
		this.dateUpdated = dateUpdated;
		this.timeUpdated = timeUpdated;
		this.status = status;
	}
}
