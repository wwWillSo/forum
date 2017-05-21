package com.forum.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.forum.entity.Post;

public class PostBean extends Post implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4665637498548893317L;
	
	private boolean haveBeenLike = false ;
	
	private boolean canUpdate = false ;
	
	private boolean canDelete = false ;
	
	private List<CommentBean> commentList = new ArrayList<CommentBean>() ;
	
	private String postTypeName ;
	
	private String loginName ;
	
	public boolean isCanUpdate() {
		return canUpdate;
	}
	public void setCanUpdate(boolean canUpdate) {
		this.canUpdate = canUpdate;
	}
	public boolean isCanDelete() {
		return canDelete;
	}
	public void setCanDelete(boolean canDelete) {
		this.canDelete = canDelete;
	}
	public String getPostTypeName() {
		return postTypeName;
	}
	public void setPostTypeName(String postTypeName) {
		this.postTypeName = postTypeName;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public List<CommentBean> getCommentList() {
		return commentList;
	}
	public void setCommentList(List<CommentBean> commentList) {
		this.commentList = commentList;
	}
	public boolean isHaveBeenLike() {
		return haveBeenLike;
	}
	public void setHaveBeenLike(boolean haveBeenLike) {
		this.haveBeenLike = haveBeenLike;
	}
	
}
