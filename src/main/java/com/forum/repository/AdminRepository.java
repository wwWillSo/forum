package com.forum.repository;

import java.io.Serializable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.forum.entity.Admin;
import com.forum.entity.Login;

public interface AdminRepository extends JpaRepository<Admin, Serializable> {
	
	public Admin findByAdminName(String adminName) ;
	
	public Admin findByAdminId(int adminId) ;
	
	@Query("select en from Admin en where en.adminName like %:keyword%")
	public Page<Admin> findByAdminNameLike(@Param("keyword")String keyword, Pageable pageable) ;
}
