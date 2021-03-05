package com.prc391.models;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity
@Table(name = "[post]")
public class Post implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name="user_username")
	private User user;
	
	@JsonManagedReference
	@OneToMany(mappedBy = "post")
	@Where(clause = "status='True'")
	private List<Comment> comments;
	
	@JsonManagedReference
	@OneToMany(mappedBy = "post")
	private List<Reaction> reactions;
	
    @Column(name = "post_content", nullable = true, length = 1024)
    private String content;
    
    @Column(name = "image_link", nullable = true, length = 1024)
    private String imageLink;
    
    @Column(name = "video_link", nullable = true, length = 1024)
    private String videoLink;
    
    @Column(name = "date_updated", nullable = false)
    private Date dateUpdated;
    
    @Column(name = "time_updated", nullable = false)
    private Time timeUpdated;

    @Column(name = "status", nullable = false)
    private boolean status;

    public boolean isLiked(String username) {
    	for (Reaction reaction : reactions) {
			if(reaction.getUser().getUsername().equals(username)) {
				return true;
			}
		}
    	return false;
    }
    
	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public List<Reaction> getReactions() {
		return reactions;
	}

	public void setReactions(List<Reaction> reactions) {
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

	public String getVideoLink() {
		return videoLink;
	}

	public void setVideoLink(String videoLink) {
		this.videoLink = videoLink;
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

	public Post(int id, User user, String content, String imageLink, String videoLink, Date dateUpdated, Time timeUpdated,
			boolean status) {
		super();
		this.user = user;
		this.content = content;
		this.imageLink = imageLink;
		this.videoLink = videoLink;
		this.dateUpdated = dateUpdated;
		this.timeUpdated = timeUpdated;
		this.status = status;
	}

	public Post() {
		super();
	}

	public Post(String content, String imageLink, String videoLink, Date dateUpdated, Time timeUpdated, boolean status) {
		super();
		this.content = content;
		this.imageLink = imageLink;
		this.videoLink = videoLink;
		this.timeUpdated = timeUpdated;
		this.dateUpdated = dateUpdated;
		this.status = status;
	}
    
    
}
