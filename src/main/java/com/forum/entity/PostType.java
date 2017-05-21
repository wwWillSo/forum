package com.forum.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the post_type database table.
 * 
 */
@Entity
@Table(name="post_type")
@NamedQuery(name="PostType.findAll", query="SELECT p FROM PostType p")
public class PostType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="post_type_id")
	private int postTypeId;

	@Column(name="post_type_name")
	private String postTypeName;

	public PostType() {
	}

	public int getPostTypeId() {
		return this.postTypeId;
	}

	public void setPostTypeId(int postTypeId) {
		this.postTypeId = postTypeId;
	}

	public String getPostTypeName() {
		return this.postTypeName;
	}

	public void setPostTypeName(String postTypeName) {
		this.postTypeName = postTypeName;
	}

}