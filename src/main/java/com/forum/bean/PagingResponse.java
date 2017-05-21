package com.forum.bean;

public class PagingResponse {
	private long number =  0 ;
	
	private int size = 5 ;
	
	private int totalPages = 1 ;
	
	private boolean firstPage = true ;
	
	private boolean lastPage = false ;
	
	private int type = 0 ;
	
	private Object object ;
	
	private String keyword = "" ;
	
	public void initFirstAndLastPage() {
		if (this.number == 0) {
			this.firstPage = true ;
			this.lastPage = false ;
		} else if (this.number + 1 == this.totalPages) {
			this.firstPage = false ;
			this.lastPage = true ;
		} else {
			this.firstPage = false ;
			this.lastPage = false ;
		}
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public long getNumber() {
		return number;
	}

	public void setNumber(long number) {
		this.number = number;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		if (totalPages != 0)
			this.totalPages = totalPages;
	}

	public boolean isFirstPage() {
		return firstPage;
	}

	public void setFirstPage(boolean firstPage) {
		this.firstPage = firstPage;
	}

	public boolean isLastPage() {
		return lastPage;
	}

	public void setLastPage(boolean lastPage) {
		this.lastPage = lastPage;
	}
	
	
}
