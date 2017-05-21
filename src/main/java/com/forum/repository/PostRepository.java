package com.forum.repository;

import java.io.Serializable;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.forum.entity.Post;

public interface PostRepository extends JpaRepository<Post, Serializable> {
	
	public Page<Post> findByPostType(int postType, Pageable pageable) ;
	
	public Page<Post> findByPostTypeAndPostTitleLike(int postType, String keyword, Pageable pageable) ;
	
	public Page<Post> findByPostTitleLike(String keyword, Pageable pageable) ;
	
	public Post findByPostId(int postId) ;
	
	@Modifying
	@Transactional
	public void deleteByPostId(int postId) ;
}
