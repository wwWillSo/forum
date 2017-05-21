package com.forum.repository;

import java.io.Serializable;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.forum.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Serializable> {
	
	public List<Comment> findByPostIdOrderByCreateTimeDesc(int postId) ;
	
	@Modifying
	@Transactional
	public void deleteByPostId(int postId) ;
}
