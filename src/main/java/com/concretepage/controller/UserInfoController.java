package com.concretepage.controller;
import java.io.Console;
import java.sql.SQLException;

import javax.validation.Valid;

import jbdc.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.concretepage.entity.Article;
import com.concretepage.service.IUserInfoService;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

@Controller
@RequestMapping("app")
public class UserInfoController {
	@Autowired
	private IUserInfoService userInfoService;
	@GetMapping("login")
	public ModelAndView login() {
		    ModelAndView mav = new ModelAndView();
		    mav.setViewName("custom-login");
		    return mav;
    }	
	@GetMapping("secure/article-details")
	public ModelAndView getAllUserArticles() {
		    ModelAndView mav = new ModelAndView();
		    mav.addObject("userArticles", userInfoService.FindAll());
		    mav.setViewName("articles");
		    return mav;
    }
	@GetMapping("secure/login")
	public ModelAndView Login() {
		    ModelAndView mav = new ModelAndView();
		    mav.addObject("userArticles", userInfoService.FindAll());
		    mav.setViewName("index-Admin");
		    return mav;
    }
	@GetMapping("")
	public ModelAndView Home() {
		    ModelAndView mav = new ModelAndView();
		    mav.addObject("userArticles", userInfoService.FindAll());
		    mav.setViewName("index");
		    return mav;
    }
	@GetMapping("error")
	public ModelAndView error() {
		    ModelAndView mav = new ModelAndView();
		    String errorMessage= "You are not authorized for the requested data.";
		    mav.addObject("errorMsg", errorMessage);
		    mav.setViewName("403");
		    return mav;
		    
    }	
	@GetMapping("Delete")
	public ModelAndView Delete(Model model,@RequestParam(value="id",required=false,defaultValue="") String id) throws ClassNotFoundException, SQLException
	{
		 ModelAndView mav = new ModelAndView();
		 System.out.print("id cua nguoi nay la"+id);
		 /*
		 PreparedStatement pr;
		 java.sql.Connection conn=ConnectionUtils.getMyConnection();
		 pr=(PreparedStatement) conn.prepareStatement("DELETE FROM articles WHERE article_id="+id);
	     pr.executeUpdate();
	     pr.close();
	     */
		 userInfoService.Delete(userInfoService.FindOne(Integer.parseInt(id)));
		 mav.setViewName("redirect:/app/secure/article-details");
		
		 return mav;
	}
	@RequestMapping(value="/app-insert",method=RequestMethod.GET)
	public ModelAndView app_insert(Model model,@RequestParam(value="id",required=false,defaultValue="") String id,
			@RequestParam(value="title",required=false,defaultValue="") String title,
			@RequestParam(value="category",required=false,defaultValue="")String category,
			@RequestParam(value="conttent",required=false,defaultValue="")String conttent) throws ClassNotFoundException, SQLException {
	    ModelAndView mav = new ModelAndView();
	    java.sql.Connection conn=ConnectionUtils.getMyConnection();
	    PreparedStatement pr;
		 pr=(PreparedStatement) conn.prepareStatement("INSERT INTO articles VALUES (?,?,?,?)");
		 pr.setString(1, id);
		 pr.setString(2, title);
		 pr.setString(3, category);
		 pr.setString(4, conttent);
	     pr.executeUpdate();
	     pr.close();
	    mav.setViewName("redirect:/app/secure/article-details");
	    return mav;
}	
	@GetMapping("insert")
	public ModelAndView insert() {
	    ModelAndView mav = new ModelAndView();
	    mav.setViewName("edit-news");
	    return mav;
}	
	@RequestMapping(value="/update")
	public ModelAndView update(Model model,@RequestParam(value="id",required=false,defaultValue="") String id) throws ClassNotFoundException, SQLException {
	    ModelAndView mav = new ModelAndView();
	    Article article=userInfoService.FindOne(Integer.parseInt(id));
	    mav.addObject("article",article);
	     mav.addObject("id", article.getArticleId());

	     mav.addObject("title", article.getTitle());

	     mav.addObject("category", article.getCategory());

	     mav.addObject("conttent", article.getConttent());

	    mav.setViewName("editnews");
	    return mav;
}	
	@RequestMapping(value="/detail")
	public ModelAndView Detail(Model model,@RequestParam(value="id",required=false,defaultValue="") String id) throws ClassNotFoundException, SQLException {
	    ModelAndView mav = new ModelAndView();
	    Article article=userInfoService.FindOne(Integer.parseInt(id));
	    mav.addObject("article",article);
	   

	  

	    mav.setViewName("Detail");
	    return mav;
}	
	@PostMapping("/save")
	public String save(@Valid Article article) {
	    
	    userInfoService.Save(article);
	   
	    return "redirect:/app/secure/article-details";
	}
} 