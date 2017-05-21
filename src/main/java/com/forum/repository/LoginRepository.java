package com.forum.repository;

import java.io.Serializable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.forum.entity.Login;

public interface LoginRepository extends JpaRepository<Login, Serializable> {
	
	public Login findByLoginName(String loginName) ;
	
	public Login findByLoginId(int loginId) ;
	
	@Query("select en from Login en where en.loginName like %:keyword%")
	public Page<Login> findByLoginNameLike(@Param("keyword")String keyword, Pageable pageable) ;
}
