package com.forum.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the post database table.
 * 
 */
@Entity
@NamedQuery(name="Post.findAll", query="SELECT p FROM Post p")
public class Post implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="post_id")
	private int postId;

	@Lob
	@Column(name="post_content")
	private String postContent;

	@Column(name="post_create_by")
	private int postCreateBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="post_create_time")
	private Date postCreateTime;

	@Column(name="post_title")
	private String postTitle;

	@Column(name="post_type")
	private int postType;

	@Column(name="post_update_by")
	private int postUpdateBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="post_update_time")
	private Date postUpdateTime;
	
	@Column(name="count_like")
	private int countLike ;
	
	@Column(name="count_comment")
	private int countComment ;
	
	@Column(name="like_content")
	private String likeContent ;

	private int status;

	public Post() {
	}

	public String getLikeContent() {
		return likeContent;
	}

	public void setLikeContent(String likeContent) {
		this.likeContent = likeContent;
	}

	public int getCountLike() {
		return countLike;
	}

	public void setCountLike(int countLike) {
		this.countLike = countLike;
	}

	public int getCountComment() {
		return countComment;
	}

	public void setCountComment(int countComment) {
		this.countComment = countComment;
	}

	public int getPostId() {
		return this.postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public String getPostContent() {
		return this.postContent;
	}

	public void setPostContent(String postContent) {
		this.postContent = postContent;
	}

	public int getPostCreateBy() {
		return this.postCreateBy;
	}

	public void setPostCreateBy(int postCreateBy) {
		this.postCreateBy = postCreateBy;
	}

	public Date getPostCreateTime() {
		return this.postCreateTime;
	}

	public void setPostCreateTime(Date postCreateTime) {
		this.postCreateTime = postCreateTime;
	}

	public String getPostTitle() {
		return this.postTitle;
	}

	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}

	public int getPostType() {
		return this.postType;
	}

	public void setPostType(int postType) {
		this.postType = postType;
	}

	public int getPostUpdateBy() {
		return this.postUpdateBy;
	}

	public void setPostUpdateBy(int postUpdateBy) {
		this.postUpdateBy = postUpdateBy;
	}

	public Date getPostUpdateTime() {
		return this.postUpdateTime;
	}

	public void setPostUpdateTime(Date postUpdateTime) {
		this.postUpdateTime = postUpdateTime;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}