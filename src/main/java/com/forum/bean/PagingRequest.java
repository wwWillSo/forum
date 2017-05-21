package com.forum.bean;

import java.io.Serializable;

public class PagingRequest implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5296008763859671234L;
	private int page = 0 ;
	private int size = 5 ;
	
	private String keyword ;
	
	private int postType = 0 ;


	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public int getPostType() {
		return postType;
	}

	public void setPostType(int postType) {
		this.postType = postType;
	}


	
}
