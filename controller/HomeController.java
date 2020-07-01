




package com.niit.shoppingcart.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.niit.shoppingcart.dao.CategoryDAO;
import com.niit.shoppingcart.dao.UserDAO;
import com.niit.shoppingcart.model.User;

@Controller
public class HomeController 
{
	@Autowired
	private UserDAO userDAO;
	
	@RequestMapping("/")
	public ModelAndView fun1()
	{
		ModelAndView m1 = new ModelAndView("index");
		return m1;
	}
	
	@ModelAttribute("user")
	public User loginUser()
	{
		return new User();
	}
	
	@RequestMapping(value = "/register")
	public ModelAndView registerPage()
	{
		return new ModelAndView("registerUserPage");
	}
	   @RequestMapping(value = "/storeUser", method = RequestMethod.POST)
			public String addUser(@Valid @ModelAttribute("user") User user, BindingResult result) 
	                                   {
			if (result.hasErrors())
	                                  {

				return "registerUserPage";
			}
 
			userDAO.saveUser(user);
			return "index";
		}
	   
	   @RequestMapping(value = "validate", method = RequestMethod.GET)
		public ModelAndView validate(HttpServletRequest request, HttpServletResponse response, HttpSession session)
				throws Exception {
			//log.debug("starting of the method checkUserOne");
			if (request.isUserInRole("ROLE_ADMIN")) {
				Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				String name = auth.getName();
				session = request.getSession(true);
				session.setAttribute("loggedInUser", name);
				ModelAndView modelAndView = new ModelAndView("adminPage");
				return modelAndView;
			} else {
				Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				String name = auth.getName();
				session = request.getSession(true);
				session.setAttribute("loggedInUser", name);
				ModelAndView modelAndView = new ModelAndView("manageProducts");
				//log.debug("Ending of the method landPage");
				return modelAndView;
			}
	   }
	   
	   @RequestMapping("/loginPage")
		public ModelAndView loginHere() {
			//log.debug("Starting of the method loginHere");
			ModelAndView mv = new ModelAndView("loginPage");
			//log.debug("Ending of the method loginHere");
			return mv;
		}

		@RequestMapping("/logout")
		public ModelAndView logOutPage() {
			//log.debug("Starting of the method logOutPage");
			ModelAndView mv = new ModelAndView("index");
			//log.debug("Ending of the method logOutPage");
			return mv;
		}

		@RequestMapping(value = "/fail2login", method = RequestMethod.GET)
		public ModelAndView loginError(ModelMap model) {
			//log.debug("Starting of the method loginError");
			return new ModelAndView("loginPage", "error", true);
		}

		 
		@RequestMapping("/landPage")
		 public ModelAndView landPage()
		 {
		// log.debug("starting of the method landPage");
		 ModelAndView mv=new ModelAndView("index");
		// log.debug("Ending of the method landPage");
		 return mv;
		 }
	}

