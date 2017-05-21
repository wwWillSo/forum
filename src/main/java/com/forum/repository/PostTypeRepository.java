package com.forum.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.forum.entity.PostType;

public interface PostTypeRepository extends JpaRepository<PostType, Serializable> {
	
	public List<PostType> findAll() ;
	
	public PostType findByPostTypeId(int postTypeId) ;
}
