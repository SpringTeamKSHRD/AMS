package com.ams.app.pagecontroller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class MainController {
	
	@RequestMapping(value = "/formuseradd", method = RequestMethod.GET)
	public String formAddUser(ModelMap model) {
		System.out.println("show form user add controller");
		return "/admin/user/useradd";
	}
	
	@RequestMapping(value = "/formlistuser", method = RequestMethod.GET)
	public String formListUsers(ModelMap model) {
		System.out.println("show form list users controller");
		return "/admin/user/viewuser";
	}

	///////////////////////////////////////////////////////////security action
	@RequestMapping(value = "/")
	public String homePage(ModelMap m) {
		System.out.println("this is home ");
		System.out.println(getUsername());
		System.out.println(getRole());
		System.out.println(isAuthenticated());
		m.addAttribute("name", getUsername());
		m.addAttribute("role", getRole());
		m.addAttribute("login", isAuthenticated());
		return "home1";
	}

	@RequestMapping(value = "/author")
	public String authorPage(ModelMap m) {
		m.addAttribute("name", getUsername());
		m.addAttribute("role", getRole());
		m.addAttribute("login", isAuthenticated());
		return "author";
	}

	@RequestMapping(value = "/admin")
	public String adminPage(ModelMap m) {
		m.addAttribute("name", getUsername());
		m.addAttribute("role", getRole());
		m.addAttribute("login", isAuthenticated());
		return "admin";
	}

	@RequestMapping(value = "/clogin", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<Map<String, Object>> login(@RequestBody User usr, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		HttpStatus status = null;
		try {
			request.login(usr.getUsername(), usr.getPassword());
			map.put("MESSAGE", "LOG IN SUCCESS");
			status = HttpStatus.OK;
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			map.put("MESSAGE", "LOG IN NOT SUCCESS");
			status = HttpStatus.NOT_FOUND;
			e.printStackTrace();
		}
		System.out.println(isAuthenticated());
		System.out.println(getRole());
		System.out.println(getUsername());
		return new ResponseEntity<Map<String, Object>>(map, status);
	}

	@RequestMapping(value = "/clogout", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<Map<String, Object>> logout(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		HttpStatus status = null;
		try {
			request.logout();
			map.put("MESSAGE", "LOG OUT SUCCESS");
			status = HttpStatus.OK;
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			map.put("MESSAGE", "LOG OUT NOT SUCCESS");
			status = HttpStatus.NOT_FOUND;
			e.printStackTrace();
		}
		return new ResponseEntity<Map<String, Object>>(map, status);
	}

	public boolean isAuthenticated() {
		return !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken);
	}

	private String getRole() {
		return SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
	}

	private String getUsername() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}
}
