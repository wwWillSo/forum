package com.forum.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.forum.bean.CommentBean;
import com.forum.bean.LoginRequest;
import com.forum.bean.LoginResponse;
import com.forum.bean.PagingRequest;
import com.forum.bean.PagingResponse;
import com.forum.bean.PostBean;
import com.forum.entity.Admin;
import com.forum.entity.Comment;
import com.forum.entity.Login;
import com.forum.entity.Post;
import com.forum.entity.PostType;
import com.forum.repository.AdminRepository;
import com.forum.repository.CommentRepository;
import com.forum.repository.LoginRepository;
import com.forum.repository.PostRepository;
import com.forum.repository.PostTypeRepository;
import com.forum.util.PasswordUtil;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private AdminRepository adminRepository ;
	
	@Autowired
	private LoginRepository loginRepository ;
	
	@Autowired
	private PostRepository postRepository ;
	
	@Autowired
	private PostTypeRepository postTypeRepository ;
	
	@Autowired
	private CommentRepository commentRepository ;
	
	/**
	 * 用户登录逻辑
	 * @param request
	 * @param httpServletRequest
	 * @return
	 */
	@RequestMapping("/userLogin")
	@ResponseBody
	public LoginResponse userLogin(@RequestBody LoginRequest request, HttpServletRequest httpServletRequest) {
		
		LoginResponse response = new LoginResponse() ;
		
		Admin admin = this.adminRepository.findByAdminName(request.getUsername()) ;
		
		if (null == admin) {
			response.setStatusCode("FAIL");
			response.setDesc("用户不存在");
			response.setRedirectUrl("/");
			return response ;
		}
		
		String password = request.getPassword() ;
		try {
			password = PasswordUtil.aesDecrypt(password, PasswordUtil._key) ;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (!password.equals(admin.getAdminPassword())) {
			response.setStatusCode("FAIL");
			response.setDesc("密码错误");
			response.setRedirectUrl("/");
			return response ;
		}
		
		admin.setAdminPassword("");
		httpServletRequest.getSession().setAttribute("admin", admin);
		response.setStatusCode("SUCCESS");
		response.setDesc("登录成功");
		response.setRedirectUrl("/admin/main");
		
		return response ;
	}
	
	/**
	 * 用户登录之后的首页，需要显示所有帖子
	 * @param model
	 * @return
	 */
	@RequestMapping("/main")
	public String main(Model model, HttpServletRequest httpServletRequest) {
		
		Admin admin = (Admin)httpServletRequest.getSession().getAttribute("admin") ;
		
		if (admin == null) {
			return "redirect:/admin" ;
		}
		
		return "/admin/main" ;
	}
	
	@RequestMapping("/gotoUserList")
	public String gotoUserList (Model model, PagingRequest param, HttpServletRequest httpServletRequest) {
		
		Admin admin = (Admin)httpServletRequest.getSession().getAttribute("admin") ;
		
		if (admin == null) {
			return "redirect:/admin" ;
		}
		
		PagingResponse response = new PagingResponse() ;
		
		Sort sort = new Sort(Direction.DESC, "loginRegistDate") ;
		PageRequest pageable = new PageRequest(param.getPage(), param.getSize(), sort) ;
		
		Page<Login> page = null ;
		
		if (param.getKeyword() != null && !param.getKeyword().isEmpty()) {
			page = this.loginRepository.findByLoginNameLike(param.getKeyword(), pageable) ;
		} else {
			page = this.loginRepository.findAll(pageable) ;
		}
		
		response.setObject(page.getContent());
		response.setKeyword(param.getKeyword()) ;
		response.setNumber(page.getNumber());
		response.setSize(page.getSize());
		response.setTotalPages(page.getTotalPages());
		response.initFirstAndLastPage();
		
		model.addAttribute("contactsPage", response) ;
		
		return "/admin/userList" ;
	}
	
	@RequestMapping("/gotoPostList")
	public String gotoPostList (Model model, PagingRequest param, HttpServletRequest httpServletRequest) {
		
		Admin admin = (Admin)httpServletRequest.getSession().getAttribute("admin") ;
		
		if (admin == null) {
			return "redirect:/admin" ;
		}
		
		PagingResponse response = new PagingResponse() ;
		
		List<PostType> typeList = this.postTypeRepository.findAll() ;
		
		Sort sort = new Sort(Direction.DESC, "postUpdateTime") ;
		PageRequest pageable = new PageRequest(param.getPage(), param.getSize(), sort) ;
		
		Page<Post> page = null ;
		
		if (param.getKeyword() != null && !param.getKeyword().isEmpty()) {	//关键字为空
			if (param.getPostType() == 0)	//查询全部
				page = this.postRepository.findByPostTitleLike(param.getKeyword(), pageable) ;
			else	//查询特定类型
				page = this.postRepository.findByPostTypeAndPostTitleLike(param.getPostType(), param.getKeyword(), pageable) ;
		} else { 															//关键字不为空
			if (param.getPostType() == 0)	//查询全部
				page = this.postRepository.findAll(pageable) ;	
			else	//查询特定类型
				page = this.postRepository.findByPostType(param.getPostType(), pageable) ;
		}
		
		List<PostBean> beanList = new ArrayList<PostBean> () ;
		
		for (Post post : page.getContent()) {
			PostBean bean = new PostBean() ;
			BeanUtils.copyProperties(post, bean);
			
			bean.setPostTypeName(this.postTypeRepository.findByPostTypeId(post.getPostType()).getPostTypeName());
			bean.setLoginName(this.loginRepository.findByLoginId(post.getPostCreateBy()).getLoginName());
			
			beanList.add(bean) ;
		}
		
		response.setObject(beanList);
		if (param.getKeyword() != null)
			response.setKeyword(param.getKeyword());
		response.setNumber(page.getNumber());
		response.setSize(page.getSize());
		response.setTotalPages(page.getTotalPages());
		response.setType(param.getPostType());
		response.initFirstAndLastPage();
		
		model.addAttribute("typeList", typeList) ;
		
		model.addAttribute("contactsPage", response) ;
		
		return "/admin/postList" ;
	}
	
	@RequestMapping("/gotoPostDetail/{postId}")
	public String gotoPostDetail (Model model, HttpServletRequest httpServletRequest, @PathVariable int postId) {
		
		Admin admin = (Admin)httpServletRequest.getSession().getAttribute("admin") ;
		
		if (admin == null) {
			return "redirect:/admin" ;
		}
		
		Post post = this.postRepository.findByPostId(postId) ;
		
		PostBean bean = new PostBean() ;
		
		String likeContent = post.getLikeContent() ;
		
		if (likeContent != null && !likeContent.isEmpty()) {
			String[] likeList = likeContent.split(",") ;
			
			likeContent = "" ;			//不知道为什么replace方法用不了，只能这么写了
			for (String loginId : likeList) {
				String loginName = this.loginRepository.findByLoginId(Integer.parseInt(loginId)).getLoginName() ;
				
				likeContent = likeContent + loginName + "," ;
			}
			
			post.setLikeContent(likeContent);
		}
		
		post.setLikeContent(likeContent);
		
		BeanUtils.copyProperties(post, bean);
		
		bean.setPostTypeName(this.postTypeRepository.findByPostTypeId(post.getPostType()).getPostTypeName());
		bean.setLoginName(this.loginRepository.findByLoginId(post.getPostCreateBy()).getLoginName());
		
		List<Comment> commentList = this.commentRepository.findByPostIdOrderByCreateTimeDesc(postId) ;
		
		List<CommentBean> commentBeanList = new ArrayList<CommentBean> () ;
		
		for (Comment comment : commentList) {
			CommentBean commentBean = new CommentBean() ;
			
			BeanUtils.copyProperties(comment, commentBean);
			
			commentBean.setLoginName(this.loginRepository.findByLoginId(comment.getLoginId()).getLoginName());
			
			commentBeanList.add(commentBean) ;
		}
		
		bean.setCommentList(commentBeanList);
		
		model.addAttribute("post", bean) ;
		
		return "/admin/postDetail" ;
	}
	
	@RequestMapping("/gotoAdminList")
	public String gotoAdminList (Model model, PagingRequest param, HttpServletRequest httpServletRequest) {
		Admin admin = (Admin)httpServletRequest.getSession().getAttribute("admin") ;
		
		if (admin == null) {
			return "redirect:/admin" ;
		}
		
		PagingResponse response = new PagingResponse() ;
		
		Sort sort = new Sort(Direction.DESC, "createTime") ;
		PageRequest pageable = new PageRequest(param.getPage(), param.getSize(), sort) ;
		
		Page<Admin> page = null ;
		
		if (param.getKeyword() != null && !param.getKeyword().isEmpty()) {
			page = this.adminRepository.findByAdminNameLike(param.getKeyword(), pageable) ;
		} else {
			page = this.adminRepository.findAll(pageable) ;
		}
		
		response.setObject(page.getContent());
		response.setKeyword(param.getKeyword()) ;
		response.setNumber(page.getNumber());
		response.setSize(page.getSize());
		response.setTotalPages(page.getTotalPages());
		response.initFirstAndLastPage();
		
		model.addAttribute("contactsPage", response) ;
		
		return "/admin/adminList" ;
	}
	
	@RequestMapping("/forbbidenUser/{loginId}")
	@ResponseBody
	public String forbbidenUser (@PathVariable int loginId, HttpServletRequest httpServletRequest) {
		
		Admin admin = (Admin)httpServletRequest.getSession().getAttribute("admin") ;
		
		if (admin == null) {
			return "redirect:/admin" ;
		}
		
		Login user = this.loginRepository.findByLoginId(loginId) ;
		
		synchronized (this.loginRepository) {
			if (user.getStatus() == 0) {			//如果用户状态正常，则禁用
				user.setStatus(1);
			} else {								//如果用户状态为禁用，则恢复
				user.setStatus(0);
			}
			this.loginRepository.save(user) ;
		}
		
		return "SUCCESS" ;
	}
	
	@RequestMapping("/deletePost/{postId}")
	@ResponseBody
	public String deletePost(@PathVariable int postId, HttpServletRequest httpServletRequest) {
		Admin admin = (Admin)httpServletRequest.getSession().getAttribute("admin") ;
		
		if (admin == null) {
			return "redirect:/admin" ;
		}
		
		synchronized (this.postRepository) {
			this.commentRepository.deleteByPostId(postId) ;
			this.postRepository.deleteByPostId(postId) ;
		}
		
		return "SUCCESS" ;
	}
	
	@RequestMapping("/gotoCreateAdmin")
	public String gotoCreateAdmin(HttpServletRequest httpServletRequest) {
		
		Admin admin = (Admin)httpServletRequest.getSession().getAttribute("admin") ;
		
		if (admin == null) {
			return "redirect:/admin" ;
		}
		
		return "/admin/createAdmin" ;
	}
	
	/**
	 * 创建管理员
	 * @param request
	 * @param httpServletRequest
	 * @return
	 */
	@RequestMapping("/createAdmin")
	@ResponseBody
	public LoginResponse userRegister(@RequestBody LoginRequest request, HttpServletRequest httpServletRequest) {
		
		LoginResponse response = new LoginResponse() ;
		
		Admin admin = (Admin)httpServletRequest.getSession().getAttribute("admin") ;
		
		if (admin == null) {
			response.setStatusCode("FAIL");
			response.setDesc("用户已存在");
			response.setRedirectUrl("/admin/gotoCreateAdmin");
			return response ;
		}
		
		Admin newAdmin = this.adminRepository.findByAdminName(request.getUsername()) ;
		
		if (null != newAdmin) {
			response.setStatusCode("FAIL");
			response.setDesc("用户已存在");
			response.setRedirectUrl("/admin/gotoCreateAdmin");
			return response ;
		}
		
		String password = request.getPassword() ;
		try {
			password = PasswordUtil.aesDecrypt(password, PasswordUtil._key) ;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			response.setStatusCode("FAIL");
			response.setDesc("密码格式有误");
			response.setRedirectUrl("/");
			return response ;
		}
		
		synchronized (this.adminRepository) {
			admin = new Admin() ;
			admin.setAdminName(request.getUsername());
			admin.setAdminPassword(password);
			admin.setCreateTime(new Date());
			
			this.adminRepository.save(admin) ;
		}
		
		response.setStatusCode("SUCCESS");
		response.setDesc("创建成功");
		response.setRedirectUrl("/admin/gotoAdminList");
		
		return response ;
	}
}
