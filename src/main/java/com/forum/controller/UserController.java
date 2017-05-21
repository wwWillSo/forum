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
import com.forum.entity.Comment;
import com.forum.entity.Login;
import com.forum.entity.Post;
import com.forum.entity.PostType;
import com.forum.repository.CommentRepository;
import com.forum.repository.LoginRepository;
import com.forum.repository.PostRepository;
import com.forum.repository.PostTypeRepository;
import com.forum.util.PasswordUtil;

@Controller
@RequestMapping("/user")
public class UserController {
	
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
		
		Login login = this.loginRepository.findByLoginName(request.getUsername()) ;
		
		if (null == login) {
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
			
			response.setStatusCode("FAIL");
			response.setDesc("密码错误");
			response.setRedirectUrl("/");
			return response ;
		}
		
		if (!password.equals(login.getLoginPassword())) {
			response.setStatusCode("FAIL");
			response.setDesc("密码错误");
			response.setRedirectUrl("/");
			return response ;
		}
		
		if (login.getStatus() == 1) {		//如果此用户已被禁用，则不能登录
			response.setStatusCode("FAIL");
			response.setDesc("此用户已被冻结");
			response.setRedirectUrl("/");
			return response ;
		}
		
		login.setLoginPassword("");
		httpServletRequest.getSession().setAttribute("user", login);
		response.setStatusCode("SUCCESS");
		response.setDesc("登录成功");
		response.setRedirectUrl("/user/main");
		
		return response ;
	}
	
	/**
	 * 用户注册逻辑
	 * @param request
	 * @param httpServletRequest
	 * @return
	 */
	@RequestMapping("/userRegister")
	@ResponseBody
	public LoginResponse userRegister(@RequestBody LoginRequest request, HttpServletRequest httpServletRequest) {
		
		LoginResponse response = new LoginResponse() ;
		
		Login login = this.loginRepository.findByLoginName(request.getUsername()) ;
		
		if (null != login) {
			response.setStatusCode("FAIL");
			response.setDesc("用户已存在");
			response.setRedirectUrl("/gotoRegister");
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
		
		synchronized (this.loginRepository) {
			login = new Login() ;
			login.setLoginName(request.getUsername());
			login.setLoginPassword(password);
			login.setLoginRegistDate(new Date());
			login.setStatus(0);
			
			this.loginRepository.save(login) ;
		}
		
		response.setStatusCode("SUCCESS");
		response.setDesc("注册成功");
		response.setRedirectUrl("/");
		
		return response ;
	}
	
	/**
	 * 用户登录之后的首页，需要显示所有帖子
	 * @param model
	 * @return
	 */
	@RequestMapping("/main")
	public String main(Model model, PagingRequest param, HttpServletRequest httpServletRequest) {
		
		Login user = (Login)httpServletRequest.getSession().getAttribute("user") ;
		
		PagingResponse response = new PagingResponse() ;
		
		List<PostType> typeList = this.postTypeRepository.findAll() ;
		
		Sort sort = new Sort(Direction.DESC, "postUpdateTime") ;
		PageRequest pageable = new PageRequest(param.getPage(), param.getSize(), sort) ;
		
		Page<Post> page = null ;
		
		if (param.getKeyword() != null && !param.getKeyword().isEmpty()) {	//关键字为空
			if (param.getPostType() == 0)	//查询全部
				page = this.postRepository.findByPostTitleLike("%" + param.getKeyword() + "%", pageable) ;
			else	//查询特定类型
				page = this.postRepository.findByPostTypeAndPostTitleLike(param.getPostType(), "%" + param.getKeyword() + "%", pageable) ;
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
			
			String likeContent = post.getLikeContent() ;
			
			if (user != null) {
				if (likeContent.contains(user.getLoginId() + ","))
					bean.setHaveBeenLike(true);
				if (bean.getPostCreateBy() == user.getLoginId()) {
					bean.setCanDelete(true);
					bean.setCanUpdate(true) ;
				}
			}
			
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
		
		return "/user/main" ;
	}
	
	@RequestMapping("/like/{postId}")
	@ResponseBody
	public String like(@PathVariable int postId, HttpServletRequest httpServletRequest) {
		
		Login user = (Login)httpServletRequest.getSession().getAttribute("user") ;
		
		Post post = this.postRepository.findByPostId(postId) ;
		
		int countLike = post.getCountLike() ;
		
		String likeContent = post.getLikeContent() ;
		
		boolean haveBeenLike = false ;
		
		if (user != null) {
			if (likeContent.contains(user.getLoginId() + ",")) 
				haveBeenLike = true ;
		
			synchronized(this.postRepository) {
				if (!haveBeenLike) {
					post.setCountLike(countLike + 1);
					post.setLikeContent(likeContent + user.getLoginId() + ",");
					this.postRepository.save(post) ;
				} else {
					post.setCountLike(countLike - 1);
					post.setLikeContent(likeContent.replace(user.getLoginId() + ",", ""));
					this.postRepository.save(post) ;
				}
			}
			return "SUCCESS" ;
		} else {
			return "FAIL" ;
		}
		
	}
	
	@RequestMapping("/gotoPostDetail/{postId}")
	public String gotoPostDetail (Model model, HttpServletRequest httpServletRequest, @PathVariable int postId) {
		
		Login user = (Login)httpServletRequest.getSession().getAttribute("user") ;
		
		Post post = this.postRepository.findByPostId(postId) ;
		
		PostBean bean = new PostBean() ;
		
		String likeContent = post.getLikeContent() ;
		
		if (user != null) {
			if (likeContent.contains(user.getLoginId() + ","))
				bean.setHaveBeenLike(true);
		}
		
		if (likeContent != null && !likeContent.isEmpty()) {
			String[] likeList = likeContent.split(",") ;
			
			likeContent = "" ;			//不知道为什么replace方法用不了，只能这么写了
			for (String loginId : likeList) {
				String loginName = this.loginRepository.findByLoginId(Integer.parseInt(loginId)).getLoginName() ;
				
				likeContent = likeContent + loginName + "," ;
			}
			
			post.setLikeContent(likeContent);
		}
		
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
		
		return "/user/postDetail" ;
	}
	
	@RequestMapping("/gotoPublish")
	public String gotoPublish (Model model) {
		
		List<PostType> typeList = this.postTypeRepository.findAll() ;
		
		model.addAttribute("typeList", typeList) ;
		
		return "/user/publish" ;
	}
	
	@RequestMapping("/publish")
	public String publish (Model model, Post post, HttpServletRequest httpServletRequest) {
		
		Date today = new Date() ;
		
		Login user = (Login)httpServletRequest.getSession().getAttribute("user") ;
		
		if (user == null) {
			model.addAttribute("noticeContent", "未登录！") ;
			return "/common/notice" ;
		}
		
		post.setCountComment(0);
		post.setCountLike(0);
		post.setLikeContent("");
		post.setPostCreateBy(user.getLoginId());
		post.setPostCreateTime(today);
		post.setPostUpdateBy(user.getLoginId());
		post.setPostUpdateTime(today);
		if (post.getPostType() == 0)
			post.setPostType(1);
		post.setStatus(0);
		
		this.postRepository.save(post) ;
		
		return "redirect:/user/main" ;
	}
	
	@RequestMapping("/comment/{postId}")
	public String comment (Model model, HttpServletRequest httpServletRequest, @PathVariable int postId, Comment comment) {
		
		Date today = new Date() ;
		
		Login user = (Login)httpServletRequest.getSession().getAttribute("user") ;
		
		if (user == null) {
			model.addAttribute("noticeContent", "未登录！") ;
			return "/common/notice" ;
		}
		
		Post post = this.postRepository.findByPostId(postId) ;
		
		synchronized (this.postRepository) {
			
			post.setCountComment(post.getCountComment() + 1);
			
			comment.setCreateTime(today);
			comment.setLoginId(user.getLoginId());
			comment.setPostId(postId);
			
			this.commentRepository.save(comment) ;
			
			this.postRepository.save(post) ;
		}
		
		return "redirect:/user/gotoPostDetail/" + postId ;
	}
	
	@RequestMapping("/deletePost/{postId}")
	@ResponseBody
	public String deletePost(@PathVariable int postId, HttpServletRequest httpServletRequest) {
		Login user = (Login)httpServletRequest.getSession().getAttribute("user") ;
		
		if (user == null) {
			return "FAIL" ;
		}

		Post post = this.postRepository.findByPostId(postId) ;
		if (post.getPostCreateBy() != user.getLoginId())
			return "FAIL" ;
		
		synchronized (this.postRepository) {
			this.commentRepository.deleteByPostId(postId) ;
			this.postRepository.deleteByPostId(postId) ;
		}
		
		return "SUCCESS" ;
	}
	
	@RequestMapping("/gotoUpdate/{postId}")
	public String gotoUpdate (Model model, @PathVariable int postId) {
		
		Post post = this.postRepository.findByPostId(postId) ;
		
		List<PostType> typeList = this.postTypeRepository.findAll() ;
		
		model.addAttribute("typeList", typeList) ;
		
		model.addAttribute("post", post) ;
		
		return "/user/updatePost" ;
	}
	
	@RequestMapping("/updatePost")
	public String updatePost (Model model, Post request, HttpServletRequest httpServletRequest) {
		
		Login user = (Login)httpServletRequest.getSession().getAttribute("user") ;
		
		if (user == null) {
			model.addAttribute("noticeContent", "未登录！") ;
			return "/common/notice" ;
		}
		
		Post post = this.postRepository.findByPostId(request.getPostId()) ;
		
		synchronized (this.postRepository) {
			post.setPostContent(request.getPostContent());
			post.setPostType(request.getPostType());
			post.setPostUpdateTime(new Date()) ;
			
			this.postRepository.save(post) ;
		}
		return "redirect:/user/main" ;
	}
}
