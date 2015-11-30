package com.ams.app.restcontroller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.ams.app.entities.UserDto;
import com.ams.app.entities.UserRoleDto;
import com.ams.app.services.UserRoleService;
import com.ams.app.services.UserService;

@RestController
@RequestMapping(value = "/api/admin/user")
public class AdminUserController {
	private static final Logger logger = LoggerFactory.getLogger(AdminArticleController.class);

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRoleService userRoleService;

	@RequestMapping(value = { "/list/{limit}/{page}", "/list/{limit}" }, method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> listUser(@PathVariable Map<String, String> pathVariables) {
		System.out.println("list user controller.");
		ArrayList<UserDto> users = null;
		
		Map<String, Object> map = new HashMap<String, Object>();
		if (pathVariables.containsKey("limit") && pathVariables.containsKey("page")) {	
			users = userService.list(Integer.parseInt(pathVariables.get("limit")),Integer.parseInt(pathVariables.get("page")));
		} else if (pathVariables.containsKey("limit")) {
			users = userService.list(Integer.parseInt(pathVariables.get("limit")), 0);
			map.put("TOTAL_PAGE",userService.getTotalPage(Integer.parseInt(pathVariables.get("limit"))));
		}
		if (users.isEmpty()) {
			map.put("MESSAGE", "USERS ARE NOT FOUND.");
			map.put("STATUS", HttpStatus.NOT_FOUND.value());
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.NOT_FOUND);
		}
		map.put("MESSAGE", "USERS HAVE BEEN FOUND.");
		map.put("STATUS", HttpStatus.OK.value());
		map.put("RESPONSE_DATA", users);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> addUser(@RequestBody UserDto user) {
		System.out.println("add controller.");		
		Map<String, Object> map = new HashMap<String, Object>();
		user.setEnable(true);
		if (userService.insertUser(user)) {			
			UserRoleDto ur=new UserRoleDto();
			ur.setId(user.getRole_id());
			ur.setUser_id(userService.getLastID());
			
			if(userRoleService.insertUserRole(ur)){
				System.out.println("success insert to user role.");
			}
			map.put("MESSAGE", "USER HAS BEEN CREATED.");
			map.put("STATUS", HttpStatus.CREATED.value());
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.CREATED);
		} else {
			map.put("MESSAGE", "USER HAS NOT BEEN CREATED.");
			map.put("STATUS", HttpStatus.NOT_FOUND.value());
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Map<String, Object>> deleteUser(@PathVariable("id") int id) {
		System.out.println("delete controller.");
		Map<String, Object> map = new HashMap<String, Object>();
		if (userService.toggle(id)) {
			map.put("MESSAGE", "USER HAS BEEN DELETED.");
			map.put("STATUS", HttpStatus.OK.value());
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		} else {
			map.put("MESSAGE", "USER HAS NOT BEEN DELETED.");
			map.put("STATUS", HttpStatus.NOT_FOUND.value());
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/", method = RequestMethod.PUT)
	public ResponseEntity<Map<String, Object>> updateUser(@RequestBody UserDto user) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (userService.updateUser(user)) {
			UserRoleDto ur=new UserRoleDto();
			ur.setId(user.getRole_id());
			ur.setUser_id(user.getId());
			if(userRoleService.updateUserRole(ur)){
				System.out.println("success update user role");
			}
			map.put("MESSAGE", "USER HAS BEEN UPDATED.");
			map.put("STATUS", HttpStatus.FOUND.value());
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		} else {
			map.put("MESSAGE", "USER HAS NOT BEEN UPDATED.");
			map.put("STATUS", HttpStatus.NOT_FOUND.value());
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getUser(@PathVariable("id") int id) {
		System.out.println("detail controller");
		Map<String, Object> map = new HashMap<String, Object>();
		UserDto user = userService.getUser(id);
		if (user != null) {
			map.put("MESSAGE", "USER HAS BEEN FOUND.");
			map.put("STATUS", HttpStatus.FOUND.value());
			map.put("RESPONSE_DATA", user);
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		} else {
			map.put("MESSAGE", "USER NOT FOUND...");
			map.put("STATUS", HttpStatus.NOT_FOUND.value());
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = { "/search/{type}/{keyword}/{limit}/{page}",
							"/search/{type}/{keyword}/{limit}" }, 
					method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> search(@PathVariable Map<String, String> pathVariables) {
		List<UserDto> listUser = null;
		Map<String, Object> map = new HashMap<String, Object>();
		HttpStatus status = null;
		if (pathVariables.containsKey("type") && pathVariables.containsKey("keyword")
				&& pathVariables.containsKey("limit") && pathVariables.containsKey("page")) {
			listUser = userService.search(pathVariables.get("type").toString(), pathVariables.get("keyword").toString(),
					Integer.parseInt(pathVariables.get("limit")), Integer.parseInt(pathVariables.get("page")));
		} else if (pathVariables.containsKey("type") && pathVariables.containsKey("keyword")
				&& pathVariables.containsKey("limit")) {
			listUser = userService.search(pathVariables.get("type").toString(), pathVariables.get("keyword").toString(),
					Integer.parseInt(pathVariables.get("limit")), 0);
		}
		if (listUser.isEmpty()) {
			map.put("MESSAGE", "RECORD NOT FOUND.");
			map.put("STATUS", HttpStatus.FOUND.value());
			status = HttpStatus.NOT_FOUND;
		} else {
			map.put("RESPONSE_DATA", listUser);
			map.put("STATUS", HttpStatus.FOUND.value());
			status = HttpStatus.OK;
		}
		return new ResponseEntity<Map<String, Object>>(map, status);
	}

	@RequestMapping(value = "/uploadimage", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> uploadImage(@RequestParam("file") MultipartFile file,
			HttpServletRequest request) {
		String filename = file.getOriginalFilename();
		System.out.println(file);
		Map<String, Object> map = new HashMap<String, Object>();
		if (!file.isEmpty()) {
			try {

				byte[] bytes = file.getBytes();

				// creating the directory to store file
				String savePath = request.getSession().getServletContext().getRealPath("resources/upload/profile");
				File path = new File(savePath);
				if (!path.exists()) {
					path.mkdir();
				}

				// creating the file on server
				File serverFile = new File(savePath + File.separator + filename);
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();

				logger.info("Server File Location=" + serverFile.getAbsolutePath());
				System.out.println(serverFile.getAbsolutePath());
				System.out.println("You are successfully uploaded file " + filename);

				map.put("MESSAGE", "UPLAOD SUCCESSED.");
				map.put("STATUS", HttpStatus.CREATED.value());
				return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);

			} catch (Exception e) {
				System.out.println("You are failed to upload " + filename + " => " + e.getMessage());
			}
		} else {
			System.out.println("You are failed to upload " + filename + " because the file was empty!");
			map.put("MESSAGE", "UPLAOD FAIL.");
			map.put("STATUS", HttpStatus.FORBIDDEN.value());
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.NOT_ACCEPTABLE);
		}
		return null;
	}
}
