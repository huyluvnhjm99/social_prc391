package com.prc391.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @Column(name = "gmail", nullable = false)
    private String gmail;

    @Column(name = "g_uid", nullable = true)
    private String gUID;

    @Column(name = "fullname", nullable = true)
    private String fullname;

    @Column(name = "image_link", nullable = true)
    private String imageLink;
   

    public String getGmail() {
		return gmail;
	}


	public void setGmail(String gmail) {
		this.gmail = gmail;
	}


	public String getgUID() {
		return gUID;
	}


	public void setgUID(String gUID) {
		this.gUID = gUID;
	}


	public String getFullname() {
		return fullname;
	}


	public void setFullname(String fullname) {
		this.fullname = fullname;
	}


	public String getImageLink() {
		return imageLink;
	}


	public void setImageLink(String imageLink) {
		this.imageLink = imageLink;
	}


	@Override
    public String toString() {
        return "User{" +
                ", gmail='" + gmail + '\'' +
                ", fullname='" + fullname + '\'' +
                ", g_uid='" + gUID + '\'' +
                ", imageLink=" + imageLink +
                '}';
    }


}

